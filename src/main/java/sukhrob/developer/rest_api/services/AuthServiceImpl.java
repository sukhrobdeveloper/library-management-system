package sukhrob.developer.rest_api.services;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.entities.VerificationCode;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.payload.*;
import sukhrob.developer.rest_api.repo.AttachmentRepository;
import sukhrob.developer.rest_api.repo.RoleRepository;
import sukhrob.developer.rest_api.repo.UserRepository;
import sukhrob.developer.rest_api.repo.VerificationCodeRepository;
import sukhrob.developer.rest_api.security.JWTProvider;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Sukhrob Tokhirov
 * @since 1.0
 *
 * <p>This class was completed on 2024-07-29 at 11:31 AM.</p>
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuthenticationManager authenticationManager;
    private final TwilioService twilioService;

    @Value(value = "${verificationCodeLimit}")
    private Integer verificationCodeLimit;

    @Value(value = "${verificationCodeTime}")
    private Long verificationCodeTime;

    @Value(value = "${verificationCodeExpiredTime}")
    private Long verificationCodeExpiryTime;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTProvider jwtProvider, VerificationCodeRepository verificationCodeRepository, AttachmentRepository attachmentRepository, AuthenticationManager authenticationManager, TwilioService twilioService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.verificationCodeRepository = verificationCodeRepository;
        this.attachmentRepository = attachmentRepository;
        this.authenticationManager = authenticationManager;
        this.twilioService = twilioService;
    }

    @Override
    public ResponseEntity<TokenDTO> signIn(LoginDTO loginDTO) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getAuthorities();
            String refreshToken = jwtProvider.generateTokenFromUUID(user.getId(), false);
            String accessToken = jwtProvider.generateTokenFromUUID(user.getId(), true);
            return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
    }

    /**
     *
     * @param phoneNumberDto contains one parameter and that's phone number which user indicated during
     *                       the registration process and I wrote a regex for phone number sequence. Mainstream of
     *                       my website would be people from Uzbekistan and regex ensures that phone number is valid
     *                       Uzbek Phone Number
     * @return Notification about verification code that's sent to phone number, String
     *
     */

    @Override
    public ResponseEntity<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - verificationCodeTime);
        Long countSendingSmsCode = verificationCodeRepository.countAllByPhoneNumberAndCreatedAtAfter(phoneNumberDto.getPhoneNumber(), timestamp);
        String code = generateCode();
        if (countSendingSmsCode >= verificationCodeLimit)
            throw new RestException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests, pls try again later!");
        boolean sendVerificationCode = twilioService.sendVerificationCode(code, phoneNumberDto.getPhoneNumber());
        if (!sendVerificationCode)
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        verificationCodeRepository.save(verificationCode);
        return ResponseEntity.ok("Verification code sent!");
    }

    /**
     *
     * @return String - verification code
     */

    private String generateCode() {
        String code = String.valueOf(Math.random() * 10000000);
        code = code.substring(0, 6);
        System.out.println(code);
        return code;
    }

    @Override
    public ResponseEntity<RegisterDto> checkCode(CodeDto codeDto) {
        Timestamp pastTime = new Timestamp(System.currentTimeMillis() - verificationCodeExpiryTime);
        VerificationCode verificationCode = verificationCodeRepository.getByCondition(
                        codeDto.getPhoneNumber(), codeDto.getCode(), pastTime)
                .orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Invalid Code!"));
        Optional<User> optionalUser = userRepository.findByUsername(codeDto.getPhoneNumber());
        String accessToken = null;
        String refreshToken = null;
        boolean registered = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            accessToken = jwtProvider.generateTokenFromUUID(user.getId(), true);
            refreshToken = jwtProvider.generateTokenFromUUID(user.getId(), false);
            registered = true;
        }
        verificationCode.setConfirmed(true);
        verificationCodeRepository.save(verificationCode);
        return ResponseEntity.ok(new RegisterDto(accessToken, refreshToken, registered));
    }

    @Override
    public ResponseEntity<TokenDTO> signUp(SignUpDto signUpDto) {
        boolean confirmed = verificationCodeRepository.existsByPhoneNumberAndConfirmedTrue(signUpDto.getPhoneNumber());
        if (!confirmed)
            throw new AuthenticationCredentialsNotFoundException("Verification code is false");

        User user = new User(signUpDto.getFirstName(),
                signUpDto.getLastName(),
                signUpDto.getPhoneNumber(),
                passwordEncoder.encode(signUpDto.getPassword()),
                roleRepository.findByName(AppConstant.USER).orElseThrow(() -> new EntityNotFoundException("Role not found!")),
                attachmentRepository.findById(signUpDto.getAttachmentId()).orElseThrow(() -> new EntityNotFoundException("Avatar not found!")),
                true
                );
        userRepository.save(user);
        String accessToken = jwtProvider.generateTokenFromUUID(user.getId(), true);
        String refreshToken = jwtProvider.generateTokenFromUUID(user.getId(), false);
        return ResponseEntity.ok(new TokenDTO(accessToken, refreshToken));
    }

    @Override
    public ResponseEntity<TokenDTO> refreshToken(TokenDTO tokenDto) {
        try {
            jwtProvider.validateToken(tokenDto.getAccessToken());
            return ResponseEntity.ok(tokenDto);
        } catch (ExpiredJwtException e) {
            try {
                jwtProvider.validateToken(tokenDto.getRefreshToken());
                UUID userId = UUID.fromString(jwtProvider.getIdFromToken(tokenDto.getRefreshToken()));
                return ResponseEntity.ok(new TokenDTO(
                        jwtProvider.generateTokenFromUUID(userId, true),
                        jwtProvider.generateTokenFromUUID(userId, false)
                ));
            } catch (Exception ex) {
                throw new RestException(HttpStatus.UNAUTHORIZED, "Refresh token expired!");
            }
        } catch (Exception e) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Access token expired!");
        }
    }

    @Override
    public UserDetails loadById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not Found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Phone number is not found!"));
    }
}

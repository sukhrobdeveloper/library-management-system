package sukhrob.developer.rest_api.services;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.net.Authenticator;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public ResponseEntity<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - verificationCodeExpiryTime);
        Long countSendingSmsCode = verificationCodeRepository.countAllByPhoneNumberAndCreatedAtAfter(phoneNumberDto.getPhoneNumber(), timestamp);
        String code = generateCode();
        if (countSendingSmsCode >= verificationCodeLimit)
            throw new RestException(HttpStatus.TOO_MANY_REQUESTS, "Ko`p urinish qildingiz, birozdan keyin urinib ko`ring!");
        boolean sendVerificationCode = twilioService.sendVerificationCode(code, phoneNumberDto.getPhoneNumber());
        if (!sendVerificationCode)
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Server xatoligi qayta urinib ko`ring!");
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        verificationCodeRepository.save(verificationCode);
        return ResponseEntity.ok("Verification code sent!");
    }

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
                .orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Xato code Yubordiz"));
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
        return null;
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
                throw new RestException(HttpStatus.UNAUTHORIZED, "Refresh token buzligan");
            }
        } catch (Exception e) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Access token buzligan");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Phone number is not found!"));
    }
}

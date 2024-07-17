package sukhrob.developer.rest_api.services;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.payload.*;
import sukhrob.developer.rest_api.repo.AttachmentRepository;
import sukhrob.developer.rest_api.repo.RoleRepository;
import sukhrob.developer.rest_api.repo.UserRepository;
import sukhrob.developer.rest_api.repo.VerificationCodeRepository;
import sukhrob.developer.rest_api.security.JWTProvider;

import java.net.Authenticator;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuthenticationManager authenticationManager;

    @Value(value = "${verificationCodeLimit}")
    private Integer verificationCodeLimit;

    @Value(value = "${verificationCodeTime}")
    private Long verificationCodeTime;

    @Value(value = "${verificationCodeExpiredTime}")
    private Long verificationCodeExpiryTime;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTProvider jwtProvider, VerificationCodeRepository verificationCodeRepository, AttachmentRepository attachmentRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.verificationCodeRepository = verificationCodeRepository;
        this.attachmentRepository = attachmentRepository;
        this.authenticationManager = authenticationManager;
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
        return null;
    }

    @Override
    public ResponseEntity<RegisterDto> checkCode(CodeDto codeDto) {
        return null;
    }

    @Override
    public ResponseEntity<TokenDTO> signUp(SignUpDto signUpDto) {
        return null;
    }

    @Override
    public ResponseEntity<TokenDTO> refreshToken(TokenDTO tokenDto) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

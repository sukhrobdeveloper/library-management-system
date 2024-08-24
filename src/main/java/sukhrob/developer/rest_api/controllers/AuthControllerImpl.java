package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.payload.*;
import sukhrob.developer.rest_api.services.AuthService;

@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenDTO> signIn(LoginDTO loginDTO) {
        return authService.signIn(loginDTO);
    }

    @Override
    public ResponseEntity<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto) {
        return authService.checkPhoneNumber(phoneNumberDto);
    }

    @Override
    public ResponseEntity<RegisterDto> checkCode(CodeDto codeDto) {
        return authService.checkCode(codeDto);
    }

    @Override
    public ResponseEntity<TokenDTO> signUp(SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }

    @Override
    public ResponseEntity<TokenDTO> refreshToken(TokenDTO tokenDto) {
        return authService.refreshToken(tokenDto);
    }

    @Override
    public ResponseEntity<User> profile(User user) {
        return ResponseEntity.ok(user);
    }
}

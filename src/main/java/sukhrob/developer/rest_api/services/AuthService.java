package sukhrob.developer.rest_api.services;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import sukhrob.developer.rest_api.payload.*;

public interface AuthService extends UserDetailsService {

    ResponseEntity<TokenDTO> signIn(LoginDTO loginDTO);

    ResponseEntity<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto);

    ResponseEntity<RegisterDto>checkCode(CodeDto codeDto);

    ResponseEntity<TokenDTO> signUp(SignUpDto signUpDto);

    ResponseEntity<TokenDTO> refreshToken(TokenDTO tokenDto);

}

package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import sukhrob.developer.rest_api.payload.*;

import java.util.UUID;

public interface AuthService extends UserDetailsService {

    ResponseEntity<TokenDTO> signIn(LoginDTO loginDTO);

    ResponseEntity<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto);

    ResponseEntity<RegisterDto>checkCode(CodeDto codeDto);

    ResponseEntity<TokenDTO> signUp(SignUpDto signUpDto);

    ResponseEntity<TokenDTO> refreshToken(TokenDTO tokenDto);

    UserDetails loadById(UUID uuid);

}

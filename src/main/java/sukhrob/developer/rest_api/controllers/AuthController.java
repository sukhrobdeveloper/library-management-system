package sukhrob.developer.rest_api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.payload.*;
import sukhrob.developer.rest_api.security.CurrentUser;
import sukhrob.developer.rest_api.utilities.AppConstant;

@RequestMapping(AppConstant.AUTH)
public interface AuthController {

    @PostMapping("sign-in")
    ResponseEntity<TokenDTO> signIn(@RequestBody @Valid LoginDTO loginDTO);


    @PostMapping("/check-phone-number")
    ResponseEntity<?> checkPhoneNumber(@RequestBody @Valid PhoneNumberDto phoneNumberDto);

    @PostMapping("/check-code")
    ResponseEntity<RegisterDto>checkCode(@RequestBody @Valid CodeDto codeDto);

    @PostMapping("/sign-up")
    ResponseEntity<TokenDTO> signUp(@RequestBody @Valid SignUpDto signUpDto);

    @PostMapping("/refresh-token")
    ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDto);

    @GetMapping("profile/")
    ResponseEntity<User> profile(@CurrentUser User user);

}

package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotNull(message = "Telefon raqamini kiritmadingiz!" )
    @Pattern(regexp = "[+][9][9][8][0-9]{9}",message = "Telefon raqamining formatini xato kiritdingiz!")
    private String username;

    @NotBlank(message = "Password should not be blank!")
    private String password;


}

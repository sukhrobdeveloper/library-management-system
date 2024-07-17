package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotNull(message = "Ismingizni kiritmadingiz!")
    @Size(min = 2, max = 100, message = "Ismingizni uzunroq kiriting(3-100)")
    private String firstName;

    private String lastName;

    @NotNull(message = "Telefon raqamini kiritmadingiz!")
    @Pattern(regexp = "[+][9][9][8][0-9]{9}", message = "Telefon raqamining formatini xato kiritdingiz!")
    private String phoneNumber;

    @NotNull(message = "sms codni kiritmadingiz!")
    @Pattern(regexp = "[0-9]{6}", message = "code xato kiritildi")
    private String code;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$")
    @NotNull(message = "Password cannot be null!")
    private String password;

    private UUID attachmentId;

}

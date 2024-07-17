package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {

    @NotNull(message = "Telefon raqamini kiritmadingiz!")
    @Pattern(regexp = "[+][9][9][8][0-9]{9}", message = "Telefon raqamining formatini xato kiritdingiz!")
    private String phoneNumber;

    @NotNull(message = "sms codni kiritmadingiz!")
    @Pattern(regexp = "[0-9]{6}", message = "code xato kiritildi")
    private String code;

}

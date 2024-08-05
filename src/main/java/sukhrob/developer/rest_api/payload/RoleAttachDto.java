package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleAttachDto {

    @NotBlank(message = "Role must not be blank!")
    private UUID roleId;

    @NotEmpty(message = "Give some user data!")
    private Set<UUID> usersId;

}

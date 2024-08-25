package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sukhrob.developer.rest_api.payload.enums.PermissionEnum;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleReqDto {

    @NotBlank(message = "Attach the role name!")
    private String name;

    private String description;

    @NotEmpty(message = "Role should have permissions!")
    private Set<PermissionEnum> permissionEnums;

}

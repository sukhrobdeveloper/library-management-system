package sukhrob.developer.rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleResDto {

    private UUID id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissions;

}


package sukhrob.developer.rest_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;
import sukhrob.developer.rest_api.payload.enums.PermissionEnum;


import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
@SQLDelete(sql = "update roles set is_deleted=true where id=?")
@SQLRestriction(value = "is_deleted=false")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role extends AbsEntity {

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private Set<PermissionEnum> permissionEnums;

}

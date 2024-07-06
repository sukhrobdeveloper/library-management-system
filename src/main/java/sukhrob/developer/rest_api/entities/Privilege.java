package sukhrob.developer.rest_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SQLDelete(sql = "update privileges set is_deleted=true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class Privilege extends AbsEntity {

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}

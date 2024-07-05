package sukhrob.developer.rest_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

@Entity(name = "measurement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLRestriction(value = "is_deleted=false")
@SQLDelete(sql = "update measurement set is_deleted = true where id = ?")
public class Measurement extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description; // to tell about the ratio or other details about this measurement

}

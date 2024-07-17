package sukhrob.developer.rest_api.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "verification_code")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update verification_code set is_deleted=true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class VerificationCode extends AbsEntity {

    private String phoneNumber;

    private String code;

    private boolean confirmed;

}

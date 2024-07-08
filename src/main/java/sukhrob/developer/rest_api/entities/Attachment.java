package sukhrob.developer.rest_api.entities;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "update attachments set is_deleted=true where id=?")
@SQLRestriction(value = "is_deleted=false")
public class Attachment extends AbsEntity {

    private String name;

    private String contentType;

    private long size;

}

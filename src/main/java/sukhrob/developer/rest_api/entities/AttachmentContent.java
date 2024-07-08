package sukhrob.developer.rest_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update attachment_content set is_deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class AttachmentContent extends AbsEntity {

    private byte[] bytes;

    @OneToOne(optional = false)
    private Attachment attachment;
}

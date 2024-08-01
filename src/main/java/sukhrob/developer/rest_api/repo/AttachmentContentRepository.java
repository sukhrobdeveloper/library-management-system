package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.AttachmentContent;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

    AttachmentContent findByAttachmentId(UUID attachmentId);

}

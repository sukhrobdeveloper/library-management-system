package sukhrob.developer.rest_api.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sukhrob.developer.rest_api.entities.Attachment;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {

    ResponseEntity<?> upload(MultipartHttpServletRequest request) throws IOException;

    ResponseEntity<Page<Attachment>> getAll(int page, int size);

    ResponseEntity<Attachment> getOne(UUID id);

    ResponseEntity<?> getFile(UUID id, HttpServletResponse response) throws IOException;

}

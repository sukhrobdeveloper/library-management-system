package sukhrob.developer.rest_api.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sukhrob.developer.rest_api.entities.Attachment;
import sukhrob.developer.rest_api.services.AttachmentService;

import java.io.IOException;
import java.util.UUID;

@RestController
public class AttachmentControllerImpl implements AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentControllerImpl(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Override
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.upload(request);
    }

    @Override
    public ResponseEntity<Page<Attachment>> getAll(int page, int size) {
        return attachmentService.getAll(page, size);
    }

    @Override
    public ResponseEntity<Attachment> getOne(UUID id) {
        return attachmentService.getOne(id);
    }

    @Override
    public ResponseEntity<?> getFile(UUID id, HttpServletResponse response) throws IOException {
        return attachmentService.getFile(id, response);
    }
}

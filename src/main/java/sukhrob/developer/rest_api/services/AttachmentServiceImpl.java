package sukhrob.developer.rest_api.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sukhrob.developer.rest_api.entities.Attachment;
import sukhrob.developer.rest_api.entities.AttachmentContent;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.repo.AttachmentContentRepository;
import sukhrob.developer.rest_api.repo.AttachmentRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @Override
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null){
            String originalFilename = file.getOriginalFilename();
            Attachment attachment=new Attachment(
                    originalFilename,
                    file.getContentType(),
                    file.getSize()
            );
            Attachment save = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent=new AttachmentContent(
                    file.getBytes(),
                    save
            );
            attachmentContentRepository.save(attachmentContent);
            return ResponseEntity.ok(attachment.getId());
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }

    @Override
    public ResponseEntity<Page<Attachment>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Attachment> attachments = attachmentRepository.findAll(pageable);
        return ResponseEntity.ok(attachments);
    }

    @Override
    public ResponseEntity<Attachment> getOne(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "File not found!"));
        return ResponseEntity.ok(attachment);
    }

    @Override
    public ResponseEntity<?> getFile(UUID id, HttpServletResponse response) throws IOException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "File topilmadi"));
        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (attachmentContent==null)
            throw new RestException(HttpStatus.NOT_FOUND,"This type of content is not found!");
        response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");
        response.setContentType(attachment.getContentType());
        FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
        return ResponseEntity.ok("Success");
    }

}

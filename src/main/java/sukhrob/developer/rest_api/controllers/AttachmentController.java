package sukhrob.developer.rest_api.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sukhrob.developer.rest_api.entities.Attachment;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.io.IOException;
import java.util.UUID;

@RequestMapping(AppConstant.ATTACHMENT)
public interface AttachmentController {

    @PostMapping("upload")
    ResponseEntity<?> upload(MultipartHttpServletRequest request) throws IOException;

    @GetMapping("/info")
    ResponseEntity<Page<Attachment>> getAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size);

    @GetMapping("info/{id}")
    ResponseEntity<Attachment> getOne(@PathVariable UUID id);

    @GetMapping("download/{id}")
    ResponseEntity<?> getFile(@PathVariable UUID id, HttpServletResponse response) throws IOException;

}

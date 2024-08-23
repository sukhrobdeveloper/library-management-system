package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.payload.CommentReqDTO;
import sukhrob.developer.rest_api.payload.CommentResDTO;
import sukhrob.developer.rest_api.services.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<CommentResDTO> createComment(CommentReqDTO commentReqDTO) {
        return commentService.createComment(commentReqDTO);
    }

    @Override
    public ResponseEntity<CommentResDTO> editComment(CommentReqDTO commentReqDTO, UUID id) {
        return commentService.editComment(commentReqDTO, id);
    }

    @Override
    public ResponseEntity<List<CommentResDTO>> viewAll(int page, int size) {
        return commentService.viewAll(page, size);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return commentService.delete(id);
    }
}

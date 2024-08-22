package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.payload.CommentReqDTO;
import sukhrob.developer.rest_api.payload.CommentResDTO;

import java.util.List;
import java.util.UUID;

@RestController
public class CommentControllerImpl implements CommentController {



    @Override
    public ResponseEntity<CommentResDTO> createComment(CommentReqDTO commentReqDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CommentResDTO> editComment(CommentReqDTO commentReqDTO, UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<CommentResDTO>> viewAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<List<CommentResDTO>> viewAllByParentId(UUID parentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return null;
    }
}

package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.payload.CommentReqDTO;
import sukhrob.developer.rest_api.payload.CommentResDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    ResponseEntity<CommentResDTO> createComment(CommentReqDTO commentReqDTO);

    ResponseEntity<CommentResDTO> editComment(CommentReqDTO commentReqDTO, UUID id);

    ResponseEntity<List<CommentResDTO>> viewAll(int page, int size);

    ResponseEntity<?> delete(UUID id);

    double getRatingByBookId(UUID id);

}

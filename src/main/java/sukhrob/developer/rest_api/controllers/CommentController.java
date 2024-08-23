package sukhrob.developer.rest_api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukhrob.developer.rest_api.payload.CommentReqDTO;
import sukhrob.developer.rest_api.payload.CommentResDTO;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.util.List;
import java.util.UUID;

@RequestMapping(AppConstant.COMMENT_CONTROLLER)
public interface CommentController {


    @PostMapping(AppConstant.ADD)
    ResponseEntity<CommentResDTO> createComment(@RequestBody @Valid CommentReqDTO commentReqDTO);

    @PutMapping(AppConstant.EDIT + "{id}")
    ResponseEntity<CommentResDTO> editComment(@RequestBody CommentReqDTO commentReqDTO, @PathVariable UUID id);

    @GetMapping(AppConstant.VIEW_ALL)
    ResponseEntity<List<CommentResDTO>> viewAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size);

    @DeleteMapping(AppConstant.DELETE + "{id}")
    ResponseEntity<?> delete(@PathVariable UUID id);


}

package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.templates.CategoryReqDTO;
import sukhrob.developer.rest_api.templates.CategoryResDTO;

import java.util.List;
import java.util.UUID;

@RestController
public class CategoryControllerImpl implements CategoryController {

    @Override
    public ResponseEntity<String> testHello() {
        return ResponseEntity.ok("Hello World from Category Controller!");
    }

    @Override
    public ResponseEntity<List<CategoryResDTO>> viewAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<CategoryResDTO> viewOne(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CategoryResDTO> add(CategoryReqDTO categoryReqDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CategoryResDTO> update(CategoryReqDTO categoryReqDTO, UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return null;
    }
}

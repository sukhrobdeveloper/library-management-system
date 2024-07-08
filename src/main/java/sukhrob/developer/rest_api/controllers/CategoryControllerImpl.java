package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.services.CategoryService;
import sukhrob.developer.rest_api.payload.CategoryReqDTO;
import sukhrob.developer.rest_api.payload.CategoryResDTO;

import java.util.List;
import java.util.UUID;

@RestController
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    public CategoryControllerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public ResponseEntity<String> testHello() {
        return ResponseEntity.ok("Hello World from Category Controller!");
    }

    @Override
    public ResponseEntity<List<CategoryResDTO>> viewAll(int page, int size) {
        return categoryService.viewAll(page, size);
    }

    @Override
    public ResponseEntity<CategoryResDTO> viewOne(UUID id) {
        return categoryService.viewOne(id);
    }

    @Override
    public ResponseEntity<CategoryResDTO> add(CategoryReqDTO categoryReqDTO) {
        return categoryService.add(categoryReqDTO);
    }

    @Override
    public ResponseEntity<CategoryResDTO> update(CategoryReqDTO categoryReqDTO, UUID id) {
        return categoryService.update(categoryReqDTO, id);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return categoryService.delete(id);
    }
}

package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Category;
import sukhrob.developer.rest_api.repo.CategoryRepository;
import sukhrob.developer.rest_api.templates.CategoryReqDTO;
import sukhrob.developer.rest_api.templates.CategoryResDTO;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ResponseEntity<String> testHello() {
        return null;
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

//    private Category findById(UUID id){
//
//    }
}

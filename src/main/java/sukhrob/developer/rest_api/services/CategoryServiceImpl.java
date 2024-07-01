package sukhrob.developer.rest_api.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Category;

import sukhrob.developer.rest_api.repo.CategoryRepository;
import sukhrob.developer.rest_api.templates.CategoryReqDTO;
import sukhrob.developer.rest_api.templates.CategoryResDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ResponseEntity<String> testHello() {
        return ResponseEntity.ok("Hello World!");
    }

    @Override
    public ResponseEntity<List<CategoryResDTO>> viewAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> pageCategory = categoryRepository.findAll(pageable);
        List<Category> categories = pageCategory.getContent();
        List<CategoryResDTO> categoryResDTOS = categories.
                stream()
                .map(this::entityToDTO)
                .toList();
        return ResponseEntity.ok(categoryResDTOS);
    }

    private CategoryResDTO entityToDTO(Category category) {
        return category.getDescription() == null ?
                new CategoryResDTO(category.getName()) :
                new CategoryResDTO(category.getName(), category.getDescription());
    }

    @Override
    public ResponseEntity<CategoryResDTO> viewOne(UUID id) {
        return ResponseEntity.ok(
                entityToDTO(
                        findById(id)
                )
        );
    }

    @Override
    public ResponseEntity<CategoryResDTO> add(CategoryReqDTO categoryReqDTO) {
        boolean exists = checkName(categoryReqDTO.getName());
        if (exists) throw new EntityExistsException("Category with this name already exists");
        Category category = new Category(
                categoryReqDTO.getName(),
                categoryReqDTO.getDescription()
        );
        categoryRepository.save(category);
        return ResponseEntity.ok(entityToDTO(category));
    }

    @Override
    public ResponseEntity<CategoryResDTO> update(CategoryReqDTO categoryReqDTO, UUID id) {
        boolean exists = checkName(categoryReqDTO.getName(), id);
        if (exists) throw new EntityExistsException("Category with this name already exists!");
        Category category = findById(id);
        category.setName(categoryReqDTO.getName());
        category.setDescription(categoryReqDTO.getDescription());
        categoryRepository.save(category);
        return ResponseEntity.ok(entityToDTO(category));
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
        return ResponseEntity.ok("Deleted Successfully!");
    }

    private Category findById(UUID id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity not found for id" + id)
        );
    }

    private boolean checkName(String name) {
        Optional<Category> optional = categoryRepository.findByName(name);
        return optional.isPresent();
    }

    private boolean checkName(String name,UUID id) {
        Optional<Category> optional = categoryRepository.findByNameAndIdNot(name, id);
        return optional.isPresent();
    }

}

package sukhrob.developer.rest_api.services;


import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.payload.CategoryReqDTO;
import sukhrob.developer.rest_api.payload.CategoryResDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    ResponseEntity<String> testHello();


    ResponseEntity<List<CategoryResDTO>> viewAll(int page, int size);


    ResponseEntity<CategoryResDTO> viewOne(UUID id);

    ResponseEntity<CategoryResDTO> add(CategoryReqDTO categoryReqDTO);

    ResponseEntity<CategoryResDTO> update(CategoryReqDTO categoryReqDTO, UUID id);

    ResponseEntity<?> delete(UUID id);

}

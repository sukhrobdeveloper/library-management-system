package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukhrob.developer.rest_api.payload.CategoryReqDTO;
import sukhrob.developer.rest_api.payload.CategoryResDTO;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.util.List;
import java.util.UUID;


@RequestMapping(AppConstant.CATEGORY)
public interface CategoryController {

    @GetMapping("/test")
    ResponseEntity<String> testHello();

    @GetMapping(AppConstant.VIEW_ALL)
    ResponseEntity<List<CategoryResDTO>> viewAll(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size);

    @GetMapping(AppConstant.VIEW_ONE + "{id}")
    ResponseEntity<CategoryResDTO> viewOne(@PathVariable UUID id);

    @PostMapping(AppConstant.ADD)
    ResponseEntity<CategoryResDTO> add(@RequestBody CategoryReqDTO categoryReqDTO);

    @PutMapping(AppConstant.EDIT + "{id}")
    ResponseEntity<CategoryResDTO> update(@RequestBody CategoryReqDTO categoryReqDTO,
                                          @PathVariable UUID id);

    @DeleteMapping(AppConstant.DELETE + "{id}")
    ResponseEntity<?> delete(@PathVariable UUID id);



}

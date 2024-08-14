package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukhrob.developer.rest_api.payload.BookResDTO;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.util.List;
import java.util.UUID;

@RequestMapping(AppConstant.BOOK_CONTROLLER)
public interface BookController {

    @GetMapping(AppConstant.VIEW_ONE + "{id}")
    ResponseEntity<BookResDTO> getBookById(@PathVariable UUID id);

    @GetMapping(AppConstant.VIEW_ALL)
    ResponseEntity<List<BookResDTO>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size);

    @GetMapping(value = "get-by-category/")
    ResponseEntity<List<BookResDTO>> getBooksByCategory(@RequestBody BookCategoryResDTO bookCategoryResDTO);





}

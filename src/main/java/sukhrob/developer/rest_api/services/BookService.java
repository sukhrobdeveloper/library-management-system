package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.payload.BookCategoryResDTO;
import sukhrob.developer.rest_api.payload.BookReqDTO;
import sukhrob.developer.rest_api.payload.BookResDTO;

import java.util.List;
import java.util.UUID;

public interface BookService {



    ResponseEntity<BookResDTO> getBookById(UUID id);


    ResponseEntity<List<BookResDTO>> getAllBooks(int page, int size);


    ResponseEntity<List<BookResDTO>> getBooksByCategory(BookCategoryResDTO bookCategoryResDTO);


    ResponseEntity<BookResDTO> add(BookReqDTO bookReqDTO);


    ResponseEntity<BookResDTO> update(BookReqDTO bookReqDTO, UUID id);


    ResponseEntity<?> delete(UUID id);

}

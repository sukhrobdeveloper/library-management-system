package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.payload.BookCategoryResDTO;
import sukhrob.developer.rest_api.payload.BookReqDTO;
import sukhrob.developer.rest_api.payload.BookResDTO;
import sukhrob.developer.rest_api.services.BookService;

import java.util.List;
import java.util.UUID;

@RestController
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<BookResDTO> getBookById(UUID id) {
        return bookService.getBookById(id);
    }

    @Override
    public ResponseEntity<List<BookResDTO>> getAllBooks(int page, int size) {
        return bookService.getAllBooks(page, size);
    }

    @Override
    public ResponseEntity<List<BookResDTO>> getBooksByCategory(BookCategoryResDTO bookCategoryResDTO) {
        return bookService.getBooksByCategory(bookCategoryResDTO);
    }

    @PreAuthorize(value = "hasAuthority('ADD_BOOKS')")
    @Override
    public ResponseEntity<BookResDTO> add(BookReqDTO bookReqDTO) {
        return bookService.add(bookReqDTO);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_BOOKS')")
    @Override
    public ResponseEntity<BookResDTO> update(BookReqDTO bookReqDTO, UUID id) {
        return bookService.update(bookReqDTO, id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_BOOKS')")
    @Override
    public ResponseEntity<?> delete(UUID id) {
        return bookService.delete(id);
    }


}

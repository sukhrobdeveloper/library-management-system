package sukhrob.developer.rest_api.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Book;
import sukhrob.developer.rest_api.entities.Category;
import sukhrob.developer.rest_api.payload.BookCategoryResDTO;
import sukhrob.developer.rest_api.payload.BookReqDTO;
import sukhrob.developer.rest_api.payload.BookResDTO;
import sukhrob.developer.rest_api.repo.BookRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, CommentService commentService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }


    @Override
    public ResponseEntity<BookResDTO> getBookById(UUID id) {
        return ResponseEntity.ok(entityToDTO(findById(id)));
    }

    private BookResDTO entityToDTO(Book book) {
        return new BookResDTO(
                book.getTitle(),
                book.getAuthors(),
                book.getPageCount(),
                book.getPrice(),
                book.getCategories().stream().map(this::getId).collect(Collectors.toSet()),
                book.getDescription(),
                book.getLanguage(),
                book.getHeight(),
                book.getWeight(),
                book.getWidth(),
                book.getMeasurement().getId(),
                book.getType(),
                book.isAvailable(),
                commentService.getRatingByBookId(book.getId())
        );
    }

    private UUID getId(Category category) {
        return category.getId();
    }

    @Override
    public ResponseEntity<List<BookResDTO>> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookResDTO> bookResDTOS = bookPage.getContent().stream()
                .map(this::entityToDTO)
                .toList();
        return ResponseEntity.ok(bookResDTOS);
    }

    @Override
    public ResponseEntity<List<BookResDTO>> getBooksByCategory(BookCategoryResDTO bookCategoryResDTO) {
        Set<Category> categories = categoryService.findAllById(bookCategoryResDTO.getCategoryIds());
        List<BookResDTO> bookResDTOS = bookRepository.findAllByCategories(categories)
                .stream()
                .map(this::entityToDTO)
                .toList();
        return ResponseEntity.ok(bookResDTOS);
    }

    @Override
    public ResponseEntity<BookResDTO> add(BookReqDTO bookReqDTO) {
        return null;
    }

    @Override
    public ResponseEntity<BookResDTO> update(BookReqDTO bookReqDTO, UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return null;
    }

    private Book findById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
    }

}

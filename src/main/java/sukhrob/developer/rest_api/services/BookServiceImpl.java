package sukhrob.developer.rest_api.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Book;
import sukhrob.developer.rest_api.entities.Category;
import sukhrob.developer.rest_api.entities.Measurement;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.payload.BookCategoryResDTO;
import sukhrob.developer.rest_api.payload.BookReqDTO;
import sukhrob.developer.rest_api.payload.BookResDTO;
import sukhrob.developer.rest_api.repo.BookRepository;
import sukhrob.developer.rest_api.repo.MeasurementRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final MeasurementRepository measurementRepository;

    public BookServiceImpl(BookRepository bookRepository, CommentService commentService, CategoryService categoryService, MeasurementRepository measurementRepository) {
        this.bookRepository = bookRepository;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.measurementRepository = measurementRepository;
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
        if (existsBook(bookReqDTO.getTitle(), bookReqDTO.getAuthors(), bookReqDTO.getCategories())) throw new RestException(HttpStatus.CONFLICT, "Duplicate name!");
        Book book = new Book(
                bookReqDTO.getTitle(),
                bookReqDTO.getAuthors(),
                bookReqDTO.getPageCount(),
                bookReqDTO.getPrice(),
                categoryService.findAllById(bookReqDTO.getCategories()),
                bookReqDTO.getDescription(),
                bookReqDTO.getLanguage(),
                bookReqDTO.getHeight(),
                bookReqDTO.getWeight(),
                bookReqDTO.getWidth(),
                measurementRepository.findById(bookReqDTO.getMeasurementId()).orElseThrow(() -> new EntityNotFoundException("Measurement doesn't exist!")),
                bookReqDTO.getType(),
                bookReqDTO.isAvailable(),
                0
        );
        bookRepository.save(book);
        return ResponseEntity.ok(entityToDTO(book));
    }

    private boolean existsBook(String title, Set<String> authors, Set<UUID> categories) {
        return bookRepository.existsByTitleAndAuthorsAndCategories(title, authors, categoryService.findAllById(categories));
    }

    @Override
    public ResponseEntity<BookResDTO> update(BookReqDTO bookReqDTO, UUID id) {
        if (existsBookAndIdNot(bookReqDTO.getTitle(), bookReqDTO.getAuthors(), bookReqDTO.getCategories(), id)) throw new RestException(HttpStatus.CONFLICT, "Duplicate name!");
        Book book = findById(id);
        book.setAuthors(bookReqDTO.getAuthors());
        book.setAvgRating(commentService.getRatingByBookId(id));
        book.setCategories(categoryService.findAllById(bookReqDTO.getCategories()));
        book.setDescription(bookReqDTO.getDescription());
        book.setTitle(bookReqDTO.getTitle());
        book.setHeight(bookReqDTO.getHeight());
        book.setWeight(bookReqDTO.getWeight());
        book.setWidth(bookReqDTO.getWidth());
        bookRepository.save(book);
        return ResponseEntity.ok(entityToDTO(book));
    }

    private boolean existsBookAndIdNot(String title, Set<String> authors, Set<UUID> categories, UUID id) {
        return bookRepository.existsByTitleAndAuthorsAndCategoriesAndIdNot(title, authors, categoryService.findAllById(categories), id);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        bookRepository.delete(findById(id));
        return ResponseEntity.ok("Success");
    }

    private Book findById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
    }

}

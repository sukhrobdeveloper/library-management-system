package sukhrob.developer.rest_api.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.config.SecurityAuditingAware;
import sukhrob.developer.rest_api.entities.Comment;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.payload.CommentReqDTO;
import sukhrob.developer.rest_api.payload.CommentResDTO;
import sukhrob.developer.rest_api.repo.BookRepository;
import sukhrob.developer.rest_api.repo.CommentRepository;
import sukhrob.developer.rest_api.repo.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SecurityAuditingAware auditingAware;


    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, BookRepository bookRepository, SecurityAuditingAware auditingAware) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.auditingAware = auditingAware;
    }

    @Override
    public ResponseEntity<CommentResDTO> createComment(CommentReqDTO commentReqDTO) {
        if (existsByBookAndUserId(commentReqDTO.getBookId(), commentReqDTO.getUserId())) throw new RestException(HttpStatus.CONFLICT, "You already commented about this book. You can change it if you want!");
        Comment comment = new Comment(
                commentReqDTO.getContent(),
                userRepository.findById(commentReqDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found!")),
                commentReqDTO.getGrade(),
                bookRepository.findById(commentReqDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book not found!")),
                commentReqDTO.getPublishDate(),
                commentReqDTO.isRecommended()
        );
        commentRepository.save(comment);
        return ResponseEntity.ok(entityToDTO(comment));
    }

    private CommentResDTO entityToDTO(Comment comment) {
        return new CommentResDTO(
                comment.getUser().getId(),
                comment.getContent(),
                comment.getGrade(),
                comment.getBook().getId(),
                comment.getPublishedDate(),
                comment.isRecommended()
        );
    }

    private boolean existsByBookAndUserId(UUID bookId, UUID userId) {
        return commentRepository.findByBookIdAndUserId(bookId, userId) != null;
    }

    @Override
    public ResponseEntity<CommentResDTO> editComment(CommentReqDTO commentReqDTO, UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<CommentResDTO>> viewAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findAll(pageable);
        List<CommentResDTO> commentResDTOS = commentPage.getContent()
                .stream()
                .map(this::entityToDTO)
                .toList();
        return ResponseEntity.ok(commentResDTOS);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        UUID userId = auditingAware.getCurrentAuditor().orElseThrow(() -> new RestException(HttpStatus.FORBIDDEN, "Method not allowed!"));
        if (existsByCommentIdAndUserId(id, userId)) throw new EntityNotFoundException("Entity not found!");
        commentRepository.delete(findById(id));
        return ResponseEntity.ok("Success!");
    }

    private boolean existsByCommentIdAndUserId(UUID id, UUID userId) {
        return commentRepository.existsByIdAndUserId(id, userId);
    }

    @Override
    public double getRatingByBookId(UUID id) {
        return commentRepository.getGradeByBookId(id);
    }

    private Comment findById(UUID id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found!"));
    }

}


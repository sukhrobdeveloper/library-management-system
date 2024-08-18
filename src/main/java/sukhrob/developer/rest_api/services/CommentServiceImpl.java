package sukhrob.developer.rest_api.services;

import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.repo.CommentRepository;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public double getRatingByBookId(UUID id) {
        return commentRepository.getGradeByBookId(id);
    }
}


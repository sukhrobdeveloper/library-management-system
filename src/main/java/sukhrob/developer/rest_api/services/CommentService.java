package sukhrob.developer.rest_api.services;

import sukhrob.developer.rest_api.entities.Book;

import java.util.Optional;
import java.util.UUID;

public interface CommentService {


    double getRatingByBookId(UUID id);

}

package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sukhrob.developer.rest_api.entities.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("select avg(grade) from comments where id = :id")
    double getGradeByBookId(@Param("id") UUID id);

    boolean existsByIdAndUserId(UUID id, UUID userId);

    @Query(value = """
            select * from comments where book = (select * from books where id = :bookId) and user = (select * from users where id = :userId)
            """, nativeQuery = true)
    Comment findByBookIdAndUserId(@Param("bookId") UUID bookId, @Param("userId") UUID userId);

}


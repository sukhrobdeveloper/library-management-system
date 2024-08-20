package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import sukhrob.developer.rest_api.entities.Book;
import sukhrob.developer.rest_api.entities.Category;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findAllByCategories(Set<Category> categories);

    boolean existsByTitleAndAuthorsAndCategories(String title, Set<String> authors, Set<Category> categories);

    boolean existsByTitleAndAuthorsAndCategoriesAndIdNot(String title, Set<String> authors, Set<Category> categories, UUID id);

}

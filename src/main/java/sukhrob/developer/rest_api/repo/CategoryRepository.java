package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.Category;

import java.util.Optional;
import java.util.UUID;


public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);

    Optional<Category> findByNameAndIdNot(String name, UUID id);

}

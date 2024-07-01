package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.Category;

import java.util.UUID;


public interface CategoryRepository extends JpaRepository<Category, UUID> {



}

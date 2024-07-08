package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}

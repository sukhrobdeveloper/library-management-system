package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.Measurement;

import java.util.Optional;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    Optional<Measurement> findByName(String name);

    Optional<Measurement> findByNameAndIdNot(String name, UUID id);
}

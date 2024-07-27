package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sukhrob.developer.rest_api.entities.VerificationCode;

import java.sql.Timestamp;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

    Long countAllByPhoneNumberAndCreatedAtAfter(String phoneNumber, Timestamp timestamp);

}

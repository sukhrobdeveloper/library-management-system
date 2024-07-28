package sukhrob.developer.rest_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sukhrob.developer.rest_api.entities.VerificationCode;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

    Long countAllByPhoneNumberAndCreatedAtAfter(String phoneNumber, Timestamp timestamp);

    @Query(value = """
            select *
            from verification_code vc
            where phone_number = :phoneNumber
              and code = :code
              and created_at > :pastTime
              and confirmed = false
            order by created_at desc
            limit 1""",nativeQuery = true)
    Optional<VerificationCode> getByCondition(@Param("phoneNumber") String phoneNumber,
                                              @Param("code") String code,
                                              @Param("pastTime") Timestamp pastTime);

}

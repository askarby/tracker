package dk.innotech.tracker.auth;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByRefreshToken(@NonNull @NotBlank String refreshToken);
}
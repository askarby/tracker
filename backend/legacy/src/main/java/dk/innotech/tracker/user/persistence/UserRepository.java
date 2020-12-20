package dk.innotech.tracker.user.persistence;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import org.reactivestreams.Publisher;

import javax.validation.constraints.NotBlank;

@Repository
public interface UserRepository extends ReactiveStreamsCrudRepository<UserEntity, Long> {

    Publisher<UserEntity> findByUsername(@NonNull @NotBlank String username);
}
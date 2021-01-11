package dk.innotech.user.repositories;

import dk.innotech.user.entities.UserRoleEntity;
import dk.innotech.user.entities.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleKey> {
    List<UserRoleEntity> findAllByUserId(Long userId);
}

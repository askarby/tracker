package dk.innotech.user.repositories;

import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
}

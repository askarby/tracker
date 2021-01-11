package dk.innotech.user.services;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.mappers.RoleMapper;
import dk.innotech.user.models.Language;
import dk.innotech.user.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public RoleDTO createRole(RoleDTO request) {
        var role = RoleEntity.builder().name(request.getName()).build();
        role.setTitle(Language.DANISH, request.getDaTitle());
        role.setTitle(Language.ENGLISH, request.getEnTitle());

        var persistedRole = roleRepository.save(role);
        return roleMapper.toRoleDto(persistedRole);
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleDto)
                .collect(toList());
    }

}

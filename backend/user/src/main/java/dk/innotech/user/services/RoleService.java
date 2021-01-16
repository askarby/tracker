package dk.innotech.user.services;

import dk.innotech.user.dtos.RoleDTO;
import dk.innotech.user.entities.RoleEntity;
import dk.innotech.user.mappers.RoleMapper;
import dk.innotech.user.models.Language;
import dk.innotech.user.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static java.util.EnumSet.of;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public RoleDTO createRole(RoleDTO request) {
        ensure(request, of(RoleChecks.Available));

        var role = RoleEntity.builder()
                .name(request.getName())
                .defaultRole(false)
                .title(Language.DANISH, request.getDaTitle())
                .title(Language.ENGLISH, request.getEnTitle())
                .build();

        var persistedRole = roleRepository.save(role);
        return roleMapper.toRoleDto(persistedRole);
    }

    @Transactional
    public RoleDTO updateRole(RoleDTO request) {
        ensure(request, of(RoleChecks.Modifiable));

        var role = RoleEntity.builder()
                .name(request.getName())
                .defaultRole(false)
                .title(Language.DANISH, request.getDaTitle())
                .title(Language.ENGLISH, request.getEnTitle())
                .build();

        var persistedRole = roleRepository.save(role);
        return roleMapper.toRoleDto(persistedRole);
    }

    @Transactional
    public void deleteRole(String name) {
        var role = RoleDTO.builder().name(name).build();
        ensure(role, of(RoleChecks.Modifiable));

        roleRepository.deleteById(name);
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Optional<RoleDTO> getRole(String name) {
        return roleRepository.findById(name).map(roleMapper::toRoleDto);
    }

    private void ensure(RoleDTO candidate, EnumSet<RoleChecks> checks) {
        // If checking for role being modifiable, it should also check for role to exist
        if (checks.contains(RoleChecks.Modifiable) && !checks.contains(RoleChecks.Exists)) {
            checks.add(RoleChecks.Exists);
        }

        var name = candidate.getName();
        var existing = getRole(name);
        if (checks.contains(RoleChecks.Available) && existing.isPresent()) {
            throw new AlreadyExistsException(String.format("Role with name '%s' already exists", name));
        } else if (checks.contains(RoleChecks.Exists) && existing.isEmpty()) {
            throw new NotExistsException(String.format("Role with name '%s' does not exist", name));
        } else if (checks.contains(RoleChecks.Modifiable) && existing.map(RoleDTO::isDefaultRole).orElseThrow()) {
            throw new UnmodifiableException(String.format("Role with name '%s' cannot be modified", name));
        }
    }

    enum RoleChecks {
        Available,
        Exists,
        Modifiable
    }
}

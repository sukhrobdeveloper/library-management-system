package sukhrob.developer.rest_api.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Role;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;
import sukhrob.developer.rest_api.repo.RoleRepository;
import sukhrob.developer.rest_api.repo.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto) {
        boolean exists = existsByName(roleReqDto.getName());
        if (exists) throw new RestException(HttpStatus.CONFLICT, "Entity already exists!");
        if (roleReqDto.getDescription() == null) roleReqDto.setDescription("Not Defined!");
        Role role = new Role(roleReqDto.getName(), roleReqDto.getDescription(), roleReqDto.getPermissionEnums());
        roleRepository.save(role);
        return ResponseEntity.ok(entityToDTO(role));
    }

    private RoleResDto entityToDTO(Role role) {
        return new RoleResDto(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissionEnums()
        );
    }

    private boolean existsByName(String name) {
        return roleRepository.findByName(name).isPresent();
    }

    @Override
    public ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto) {
        Role role = getById(roleAttachDto.getRoleId());
        List<User> users = userRepository.findAllById(roleAttachDto.getUsersId());
        if (users.size() != roleAttachDto.getUsersId().size())
            throw new RestException(HttpStatus.CONFLICT, "Something went wrong! Try again later");
        for (User user: users) {
            user.setRole(role);
        }
        userRepository.saveAll(users);
        return ResponseEntity.ok("Success!");
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(roleRepository.findAll().stream().map(this::entityToDTO).toList());
    }

    @Override
    public ResponseEntity<RoleResDto> getPermissionById(UUID id) {
        return ResponseEntity.ok(entityToDTO(getById(id)));
    }

    private Role getById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role with this id not found!"));
    }

    @Override
    public ResponseEntity<Set<PermissionEnum>> getAllPermission() {
        List<PermissionEnum> permissions = Arrays.asList(PermissionEnum.values());
        return ResponseEntity.ok(Set.copyOf(permissions));
    }



}

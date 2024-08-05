package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;
import sukhrob.developer.rest_api.repo.RoleRepository;

import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<RoleResDto> getPermissionById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Set<PermissionEnum>> getAllPermission() {
        return null;
    }



}

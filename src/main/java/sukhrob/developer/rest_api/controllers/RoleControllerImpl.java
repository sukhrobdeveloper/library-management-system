package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;
import sukhrob.developer.rest_api.services.RoleService;

import java.util.Set;
import java.util.UUID;

@RestController
public class RoleControllerImpl implements RoleController{

    private final RoleService roleService;

    public RoleControllerImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto) {
        return roleService.add(roleReqDto);
    }

    @Override
    public ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto) {
        return roleService.attachRole(roleAttachDto);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return roleService.getAll();
    }

    @Override
    public ResponseEntity<RoleResDto> getPermissionById(UUID id) {
        return roleService.getPermissionById(id);
    }

    @Override
    public ResponseEntity<Set<PermissionEnum>> getAllPermission() {
        return roleService.getAllPermission();
    }
}

package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.payload.enums.PermissionEnum;
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

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @Override
    public ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto) {
        return roleService.add(roleReqDto);
    }

    @PreAuthorize(value = "hasAuthority('ATTACH_ROLE')")
    @Override
    public ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto) {
        return roleService.attachRole(roleAttachDto);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ALL_ROLES')")
    @Override
    public ResponseEntity<?> getAll() {
        return roleService.getAll();
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE_BY_ID')")
    @Override
    public ResponseEntity<RoleResDto> getPermissionById(UUID id) {
        return roleService.getPermissionById(id);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ALL_PERMISSIONS')")
    @Override
    public ResponseEntity<Set<PermissionEnum>> getAllPermission() {
        return roleService.getAllPermission();
    }
}

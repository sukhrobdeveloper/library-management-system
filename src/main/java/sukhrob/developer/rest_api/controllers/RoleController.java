package sukhrob.developer.rest_api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;
import sukhrob.developer.rest_api.utilities.AppConstant;


import java.util.Set;
import java.util.UUID;

@RequestMapping(AppConstant.ROLE_CONTROLLER)
public interface RoleController {

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping
    ResponseEntity<RoleResDto> add(@RequestBody @Valid RoleReqDto roleReqDto);


    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping(AppConstant.ATTACH_ROLE)
    ResponseEntity<?> attachRole(@RequestBody @Valid RoleAttachDto roleAttachDto);

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping
    ResponseEntity<?> getAll();

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/role-id/{id}")
    ResponseEntity<RoleResDto> getPermissionById(@PathVariable UUID id);

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/permissions")
    ResponseEntity<Set<PermissionEnum>> getAllPermission();

}

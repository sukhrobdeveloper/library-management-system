package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.payload.enums.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto);



    ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto);



    ResponseEntity<?> getAll();



    ResponseEntity<RoleResDto> getPermissionById(UUID id);



    ResponseEntity<Set<PermissionEnum>> getAllPermission();

}

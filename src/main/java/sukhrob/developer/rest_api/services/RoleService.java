package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.payload.RoleAttachDto;
import sukhrob.developer.rest_api.payload.RoleReqDto;
import sukhrob.developer.rest_api.payload.RoleResDto;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    public ResponseEntity<RoleResDto> add(RoleReqDto roleReqDto);



    public ResponseEntity<?> attachRole(RoleAttachDto roleAttachDto);



    public ResponseEntity<?> getAll();



    public ResponseEntity<RoleResDto> getPermissionById(UUID id);



    public ResponseEntity<Set<PermissionEnum>> getAllPermission();

}

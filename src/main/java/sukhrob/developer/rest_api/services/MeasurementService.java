package sukhrob.developer.rest_api.services;

import org.springframework.http.ResponseEntity;
import sukhrob.developer.rest_api.templates.MeasurementReqDTO;
import sukhrob.developer.rest_api.templates.MeasurementResDTO;

import java.util.List;
import java.util.UUID;

public interface MeasurementService {

    ResponseEntity<MeasurementResDTO> viewOne(UUID id);

    ResponseEntity<List<MeasurementResDTO>> viewAll(int page, int size);

    ResponseEntity<MeasurementResDTO> add(MeasurementReqDTO measurementReqDTO);

    ResponseEntity<MeasurementResDTO> update(UUID id, MeasurementReqDTO measurementReqDTO);

    ResponseEntity<?> delete(UUID id);

}

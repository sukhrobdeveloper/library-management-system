package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sukhrob.developer.rest_api.services.MeasurementService;
import sukhrob.developer.rest_api.templates.MeasurementReqDTO;
import sukhrob.developer.rest_api.templates.MeasurementResDTO;

import java.util.List;
import java.util.UUID;

@RestController
public class MeasurementControllerImpl implements MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementControllerImpl(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public ResponseEntity<MeasurementResDTO> viewOne(UUID id) {
        return measurementService.viewOne(id);
    }

    @Override
    public ResponseEntity<List<MeasurementResDTO>> viewAll(int page, int size) {
        return measurementService.viewAll(page, size);
    }

    @Override
    public ResponseEntity<MeasurementResDTO> add(MeasurementReqDTO measurementReqDTO) {
        return measurementService.add(measurementReqDTO);
    }

    @Override
    public ResponseEntity<MeasurementResDTO> update(MeasurementReqDTO measurementReqDTO, UUID id) {
        return measurementService.update(id, measurementReqDTO);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return measurementService.delete(id);
    }
}

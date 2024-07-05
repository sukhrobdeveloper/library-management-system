package sukhrob.developer.rest_api.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sukhrob.developer.rest_api.entities.Measurement;
import sukhrob.developer.rest_api.repo.MeasurementRepository;
import sukhrob.developer.rest_api.templates.MeasurementReqDTO;
import sukhrob.developer.rest_api.templates.MeasurementResDTO;

import java.util.List;
import java.util.UUID;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public ResponseEntity<MeasurementResDTO> viewOne(UUID id) {
        return ResponseEntity.ok(entityToDTO(findById(id)));
    }

    @Override
    public ResponseEntity<List<MeasurementResDTO>> viewAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Measurement> measurementPage = measurementRepository.findAll(pageRequest);
        List<MeasurementResDTO> measurementResDTOS = measurementPage.getContent()
                .stream()
                .map(this::entityToDTO)
                .toList();

        return ResponseEntity.ok(measurementResDTOS);
    }

    @Override
    public ResponseEntity<MeasurementResDTO> add(MeasurementReqDTO measurementReqDTO) {
        boolean exists = checkName(measurementReqDTO.getName());
        if (exists) throw new EntityExistsException("This entity already exists!");
        Measurement measurement = new Measurement(
                measurementReqDTO.getName(),
                measurementReqDTO.getDescription()
        );
        measurementRepository.save(measurement);
        return ResponseEntity.ok(entityToDTO(measurement));
    }

    @Override
    public ResponseEntity<MeasurementResDTO> update(UUID id, MeasurementReqDTO measurementReqDTO) {
        boolean exists = checkName(measurementReqDTO.getName(), id);
        if (exists) throw new EntityExistsException("This entity already exists!");
        Measurement measurement = findById(id);
        if (measurementReqDTO.getDescription() != null) {
            measurement.setDescription(measurement.getDescription());
        }
        measurement.setName(measurement.getName());
        measurementRepository.save(measurement);
        return ResponseEntity.ok(entityToDTO(measurement));
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        Measurement measurement = findById(id);
        measurementRepository.delete(measurement);
        return ResponseEntity.ok("Deleted successfully!");
    }

    private boolean checkName(String name) {
        return measurementRepository.findByName(name).isPresent();
    }

    private boolean checkName(String name, UUID id) {
        return measurementRepository.findByNameAndIdNot(name, id).isPresent();
    }

    private MeasurementResDTO entityToDTO(Measurement measurement) {
        return measurement.getDescription() != null ? new MeasurementResDTO(
                measurement.getName(),
                measurement.getDescription()
        ) : new MeasurementResDTO(measurement.getName());
    }

    private Measurement findById(UUID id) {
        return measurementRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("This entity doesn't exist!")
        );
    }
}

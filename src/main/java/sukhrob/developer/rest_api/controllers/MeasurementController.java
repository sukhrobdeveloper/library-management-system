package sukhrob.developer.rest_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukhrob.developer.rest_api.payload.MeasurementReqDTO;
import sukhrob.developer.rest_api.payload.MeasurementResDTO;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.util.List;
import java.util.UUID;

@RequestMapping(AppConstant.MEASUREMENT)
public interface MeasurementController {

    @GetMapping(AppConstant.VIEW_ONE + "{id}")
    ResponseEntity<MeasurementResDTO> viewOne(@PathVariable UUID id);

    @GetMapping(AppConstant.VIEW_ALL)
    ResponseEntity<List<MeasurementResDTO>> viewAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size);

    @PostMapping(AppConstant.ADD)
    ResponseEntity<MeasurementResDTO> add(@RequestBody MeasurementReqDTO measurementReqDTO);

    @PutMapping(AppConstant.EDIT + "{id}")
    ResponseEntity<MeasurementResDTO> update(@RequestBody MeasurementReqDTO measurementReqDTO,
                                             @PathVariable UUID id);

    @DeleteMapping(AppConstant.DELETE + "{id}")
    ResponseEntity<?> delete(@PathVariable UUID id);

}

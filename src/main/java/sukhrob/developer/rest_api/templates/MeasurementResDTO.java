package sukhrob.developer.rest_api.templates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementResDTO {

    private String name;

    private String description;

    public MeasurementResDTO(String name) {
        this.name = name;
    }
}

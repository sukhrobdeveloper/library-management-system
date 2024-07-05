package sukhrob.developer.rest_api.templates;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasurementReqDTO {

    @NotNull(message = "name cannot be empty!")
    private String name;

    private String description;

}

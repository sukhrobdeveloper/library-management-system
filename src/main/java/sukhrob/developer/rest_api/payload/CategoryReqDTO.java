package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDTO {

    @NotNull(message = "name cannot be null!")
    private String name;

    private String description;

}

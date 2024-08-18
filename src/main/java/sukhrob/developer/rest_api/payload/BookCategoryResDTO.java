package sukhrob.developer.rest_api.payload;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCategoryResDTO {

    @NotBlank(message = "Provide at least one category!")
    private Set<UUID> categoryIds;

}

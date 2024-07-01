package sukhrob.developer.rest_api.templates;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResDTO {

    private String name;

    private String description;

    public CategoryResDTO(String name) {
        this.name = name;
        this.description = "not provided";
    }
}

package sukhrob.developer.rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sukhrob.developer.rest_api.utilities.BookType;
import sukhrob.developer.rest_api.utilities.Language;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookResDTO {

    private String title;

    private Set<String> authors;

    private int pageCount;

    private double price;

    private Set<UUID> categories;

    private String description;

    private Language language;

    private double height;

    private double weight;

    private double width;

    private UUID measurementId;

    private BookType type;

    private boolean isAvailable;


}

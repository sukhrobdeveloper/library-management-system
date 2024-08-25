package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sukhrob.developer.rest_api.payload.enums.BookType;
import sukhrob.developer.rest_api.payload.enums.Language;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookReqDTO {

    @NotNull(message = "Title cannot be empty!")
    private String title;

    @NotBlank
    private Set<String> authors;

    private int pageCount;

    private double price;

    @NotBlank(message = "Please select at least one category!")
    private Set<UUID> categories;

    @NotNull
    private String description;

    @NotBlank(message = "Please select a language of the book!")
    private Set<Language> language;

    private double height;

    private double weight;

    private double width;

    private UUID measurementId;

    @NotBlank(message = "Select a book type!")
    private Set<BookType> type;

    private boolean isAvailable = false;

}

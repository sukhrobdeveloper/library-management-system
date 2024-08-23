package sukhrob.developer.rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDTO {

    @NotBlank
    private UUID userId;

    @NotNull(message = "Please describe your experience!")
    private String content;

    private Integer grade;

    @NotBlank
    private UUID bookId;

    private Date publishDate;

    private boolean isRecommended;

}

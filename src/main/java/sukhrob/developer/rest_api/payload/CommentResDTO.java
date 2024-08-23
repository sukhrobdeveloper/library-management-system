package sukhrob.developer.rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDTO {

    private UUID userId;

    private String content;

    private Integer grade;

    private UUID bookId;

    private Date publishDate;

    private boolean isRecommended;

}

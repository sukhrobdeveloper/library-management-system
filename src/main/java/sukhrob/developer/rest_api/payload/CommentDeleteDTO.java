package sukhrob.developer.rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteDTO {

    private UUID userId;

    private UUID commentId;

}

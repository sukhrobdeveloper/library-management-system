package sukhrob.developer.rest_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "update comments set is_deleted = true where id = ?")
@SQLRestriction(value = "is_deleted=false")
@JsonIgnoreProperties(value = {"createdAt"},
        allowGetters = true)
public class Comment extends AbsEntity implements Serializable {

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date publishedDate;

    private boolean isRecommended;

}

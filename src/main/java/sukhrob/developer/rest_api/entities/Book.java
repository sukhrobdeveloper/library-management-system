package sukhrob.developer.rest_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;
import sukhrob.developer.rest_api.utilities.BookType;
import sukhrob.developer.rest_api.utilities.Language;

import java.util.List;

@Entity(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "update books set is_deleted=true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class Book extends AbsEntity {

    @ElementCollection
    private List<String> authors;

    private int pageCount;

    private double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "category", nullable = false)
    private List<Category> categories;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    private double height;

    private double width;

    @ManyToOne(fetch = FetchType.LAZY)
    private Measurement measurement;

    @Enumerated(value = EnumType.STRING)
    private BookType type;

}

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
import java.util.Set;

@Entity(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "update books set is_deleted=true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class Book extends AbsEntity {

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "author", nullable = false)
    private List<String> authors;

    private int pageCount;

    private double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Category_Books_mapping", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    private double height;

    private double weight;

    private double width;

    @ManyToOne(fetch = FetchType.LAZY)
    private Measurement measurement;

    @Enumerated(value = EnumType.STRING)
    private BookType type;

}

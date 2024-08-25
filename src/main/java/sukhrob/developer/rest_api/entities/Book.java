package sukhrob.developer.rest_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;
import sukhrob.developer.rest_api.payload.enums.BookType;
import sukhrob.developer.rest_api.payload.enums.Language;

import java.util.Set;

@Entity(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "update books set is_deleted=true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class Book extends AbsEntity {

    @Column(nullable = false)
    private String title;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "author", nullable = false)
    private Set<String> authors;

    private int pageCount;

    private double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Category_Books_mapping", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    private String description;

    @ElementCollection(targetClass = BookType.class)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Enumerated(value = EnumType.STRING)
    @Column(name = "language")
    private Set<Language> language;

    private double height;

    private double weight;

    private double width;

    @ManyToOne(fetch = FetchType.LAZY)
    private Measurement measurement;

    @ElementCollection(targetClass = BookType.class)
    @CollectionTable(name = "book_types", joinColumns = @JoinColumn(name = "book_id"))
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Set<BookType> type;

    private boolean isAvailable;

    private double avgRating;

}

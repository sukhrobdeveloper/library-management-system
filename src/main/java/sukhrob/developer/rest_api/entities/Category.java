package sukhrob.developer.rest_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

import java.util.Set;


@Entity(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update categories set is_deleted = true where id = ?")
@SQLRestriction(value = "is_deleted=false")
public class Category extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Category_Books_mapping", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

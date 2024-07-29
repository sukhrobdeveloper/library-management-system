package sukhrob.developer.rest_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sukhrob.developer.rest_api.entities.template.AbsEntity;

import java.util.Collection;


@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SQLDelete(sql = "update users set is_deleted=true where id=?")
@SQLRestriction(value = "is_deleted=false")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    private boolean isAccountNonExpired = true;
    private boolean enabled;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;

    public User(String firstName, String lastName, String username, String password, Role role, Attachment avatar, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissionEnums().stream()
                .map(permissionEnum ->
                        new SimpleGrantedAuthority(permissionEnum.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }
}

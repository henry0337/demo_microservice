package com.demo.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    String id;

    @Column(name = "display_name", nullable = false)
    @Getter
    String name;

    @Getter
    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Getter
    @Builder.Default
    String avatar = "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small_2x/default-avatar-icon-of-social-media-user-vector.jpg";

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Getter
    Role role = Role.USER;

    @ColumnDefault("0")
    @Builder.Default
    @Getter
    short isAccountExpired = 0;

    @ColumnDefault("0")
    @Builder.Default
    @Getter
    short isAccountLocked = 0;

    @ColumnDefault("0")
    @Builder.Default
    @Getter
    short isCredentialsExpired = 0;

    @ColumnDefault("1")
    @Builder.Default
    @Getter
    short status = 1;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(updatable = false, nullable = false)
    @Getter
    Date createdAt = new Date();

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(insertable = false)
    @Getter
    Date updatedAt = new Date();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isAccountExpired == 0;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked == 0;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsExpired == 0;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}

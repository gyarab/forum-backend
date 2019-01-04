package cloud.forum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity
@Getter
public class ForumUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    @Column(unique = true)
    private String username;

    private boolean enabled;
    @Transient
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role", length = 50)
    private Set<String> roles;

    public ForumUser(String username, String password, boolean enabled, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    ForumUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null) {
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}

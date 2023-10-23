package org.gfg.UserService.model;

import lombok.*;
import org.gfg.Utils.UserIdentifier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Getter
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(unique = true, nullable = false)
    private String contact;

    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String authorities;

    private UserIdentifier userIdentifier;

    private String userIdentifierValue;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(",")).map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return contact;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

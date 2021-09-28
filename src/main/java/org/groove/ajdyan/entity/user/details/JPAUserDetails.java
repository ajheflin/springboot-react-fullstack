package org.groove.ajdyan.entity.user.details;

import lombok.extern.slf4j.Slf4j;
import org.groove.ajdyan.entity.authority.Authority;
import org.groove.ajdyan.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JPAUserDetails  implements UserDetails {
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;
    public JPAUserDetails(User user) {
        //Set all values
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.active = user.isActive();
        //Turn string into a list of SimpleGrantedAuthorities
        this.authorities = user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //More boilerplate
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //Hardwired account valid switches
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
        return active;
    }
}

package org.groove.ajdyan.providers;

import org.groove.ajdyan.entity.authority.Authority;
import org.groove.ajdyan.entity.authority.AuthorityService;
import org.groove.ajdyan.entity.user.User;
import org.groove.ajdyan.entity.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 9/28/2021
 * Time: 11:16 AM
 **/
@Component
public class PromotionProvider {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Transactional
    public void makeAdmin(Long id) {
        Optional<User> user = userService.findUser(id);
        user.orElseThrow(() -> new UsernameNotFoundException("No user was found with this username."));
        Optional<Authority> admin = authorityService.findAuthority("ROLE_ADMIN");
        admin.get().addUser(user.get());
    }
    @Transactional
    public void makeUser(Long uid) {
        Optional<User> user = userService.findUser(uid);
        user.orElseThrow(() -> new UsernameNotFoundException("No user was found with this username."));
        Optional<Authority> users = authorityService.findAuthority("ROLE_USER");
        users.get().addUser(user.get());
    }
}

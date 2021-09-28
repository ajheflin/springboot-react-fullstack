package org.groove.ajdyan.entity.user.details;

import org.groove.ajdyan.entity.user.User;
import org.groove.ajdyan.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JPAUserDetailsService implements UserDetailsService {
    //Autowire the userrepository service bean
    @Autowired
    UserRepository userRepository;

    //Find a user by username
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));

        return user.map(JPAUserDetails::new).get();
    }
}

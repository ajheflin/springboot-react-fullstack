package org.groove.ajdyan.controller;

import org.groove.ajdyan.entity.user.User;
import org.groove.ajdyan.entity.user.UserService;
import org.groove.ajdyan.models.AuthenticationRequest;
import org.groove.ajdyan.models.AuthenticationResponse;
import org.groove.ajdyan.entity.user.details.JPAUserDetailsService;
import org.groove.ajdyan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 9/28/2021
 * Time: 9:15 AM
 **/
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    JPAUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch(BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final Optional<User> user = userService.findUserByUsername(authenticationRequest.getUsername());
        final User resolvedUser = user.orElseThrow(Exception::new);

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, resolvedUser.getUsername(), resolvedUser.getName(), resolvedUser.getAuthorities(), resolvedUser.getId()));
    }
}

package org.groove.ajdyan.models;

import org.groove.ajdyan.entity.authority.Authority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 9/28/2021
 * Time: 9:53 AM
 **/
public class AuthenticationResponse {
    private final String jwt;
    private final String username;
    private final String name;
    private final List<Authority> authorities;
    private final Long id;

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthorities() {
        return authorities.stream().map(Authority::getAuthority).collect(Collectors.toList());
    }

    public Long getId() { return id; }

    public AuthenticationResponse(String jwt, String username, String name, List<Authority> authorities, Long id) {
        this.jwt = jwt;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
        this.id = id;
    }
}

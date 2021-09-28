package org.groove.ajdyan.entity.authority;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 8/26/2021
 * Time: 10:24 AM
 **/
@Service
@Data
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public Optional<Authority> findAuthority(String roleName) {
        return authorityRepository.findByAuthority(roleName);
    }
}

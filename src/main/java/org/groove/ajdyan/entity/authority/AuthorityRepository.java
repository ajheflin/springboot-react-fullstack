package org.groove.ajdyan.entity.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 8/26/2021
 * Time: 10:24 AM
 **/
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByAuthority(String authority);
}

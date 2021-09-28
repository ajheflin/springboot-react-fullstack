package org.groove.ajdyan.entity.authority;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.groove.ajdyan.entity.user.User;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 8/26/2021
 * Time: 10:07 AM
 **/
@Entity
@Table
@Data
@NoArgsConstructor
@Slf4j
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authority;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Authority(String s) {
        authority = s;
        users = new ArrayList<>();
    }

    @Transactional
    public void addUser(User user) {
        users.add(user);
        user.addAuthority(this);
    }
}

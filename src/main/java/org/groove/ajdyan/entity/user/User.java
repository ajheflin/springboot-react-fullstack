package org.groove.ajdyan.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.groove.ajdyan.entity.authority.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

//Entity/Table annotations
@Entity
@Table
//Generate ToString, EqualsAndHashCode, Getter/Setter, RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class User {
    @Id
    @GeneratedValue(
            strategy=IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private boolean active;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Authority> authorities;

    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("name") String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.active = true;
        this.authorities = new ArrayList<>();
    }

    public User(String username, String password, String name, boolean active) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.active = active;
        this.authorities = new ArrayList<>();
        this.authorities.forEach(x -> x.getUsers().add(this));
    }

    public boolean hasRole(String role) {
        return authorities.stream().anyMatch(thisRole -> thisRole.getAuthority().equals(role));
    }
    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }
}

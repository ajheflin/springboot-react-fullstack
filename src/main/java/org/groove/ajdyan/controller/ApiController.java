package org.groove.ajdyan.controller;

import org.groove.ajdyan.entity.authority.Authority;
import org.groove.ajdyan.entity.user.User;
import org.groove.ajdyan.providers.PromotionProvider;
import org.groove.ajdyan.entity.user.UserService;
import org.groove.ajdyan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * User: AJ Heflin
 * Date: 9/28/2021
 * Time: 10:55 AM
 **/
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private PromotionProvider promotionProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HashMap<String, Long> getUsers() {
        return userService.getUsers();
    }
    @RequestMapping(value = "/promote/admin/{id}", method = RequestMethod.PUT)
    public void makeAdmin(@PathVariable Long id) {
        promotionProvider.makeAdmin(id);
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        Long uid = userService.getUserId(user);
        promotionProvider.makeUser(uid);
    }
    @RequestMapping(value = "/user/authorities/{id}", method = RequestMethod.GET)
    public List<String> getRoles(@PathVariable Long id, HttpServletRequest request) throws Exception {
        String jwt = request.getHeader("Authorization");
        if(jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            Optional<User> user = userService.findUserByUsername(username);
            List<String> authorities = user.orElseThrow(Exception::new).getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList());
            if(authorities.stream().anyMatch(role -> role.equals("ROLE_ADMIN"))) {
                return userService.findUser(id).orElseThrow(Exception::new).getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList());
            }
            return authorities;
        }
        throw new Exception("No valid JWT found");
    }
    @RequestMapping(value = "/user/id", method = RequestMethod.GET)
    public Long getId(HttpServletRequest request) throws Exception {
        String jwt = request.getHeader("Authorization");
        if(jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            Optional<User> user = userService.findUserByUsername(username);
            return user.orElseThrow(Exception::new).getId();
        }
        throw new Exception("Invalid JWT.");
    }
    @RequestMapping(value = "/user/name", method = RequestMethod.GET)
    public String getName(HttpServletRequest request) throws Exception {
        String jwt = request.getHeader("Authorization");
        if(jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            Optional<User> user = userService.findUserByUsername(username);
            return user.orElseThrow(Exception::new).getName();
        }
        throw new Exception("Invalid JWT.");
    }
}

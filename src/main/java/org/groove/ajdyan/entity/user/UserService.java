package org.groove.ajdyan.entity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public HashMap<String, Long> getUsers() {
        HashMap<String, Long> result = new HashMap<>();
        userRepository.findAll().stream().sorted(Comparator.comparing(User::getId)).forEach(mapped -> result.put(mapped.getUsername(), mapped.getId()));
        return result;
    }

    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Long getUserId(User user) {
        return userRepository.findByUsername(user.getUsername()).get().getId();
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

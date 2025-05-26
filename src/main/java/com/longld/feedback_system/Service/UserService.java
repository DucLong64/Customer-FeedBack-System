package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.User;

import java.util.Optional;

public interface UserService {
    public User crateUser(String username, String password);
    public Optional<User> findByUsername(String username);
    public Optional<User> findById(Long id);
}

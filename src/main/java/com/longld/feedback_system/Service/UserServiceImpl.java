package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User crateUser(String username, String password) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }
    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username) ;
    }
    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
}

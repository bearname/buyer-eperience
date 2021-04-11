package com.example.restservice.service;

import com.example.restservice.exceptitons.FailedSaveUserException;
import com.example.restservice.model.User;
import com.example.restservice.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownServiceException;

//@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String email) throws UnknownServiceException {
        try {
            User user = new User(email);
             userRepository.save(user);
        } catch (ConstraintViolationException exception) {
            throw new UnknownServiceException("duplicate user");
        }
    }
}

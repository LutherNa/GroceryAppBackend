package com.revature.vanqapp.service;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.UserRepository;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

    public boolean validate(User user) throws InvalidCredentialsException {
        return userRepository.findByUsername(user.getUsername()).orElseThrow(InvalidCredentialsException::new)
                .getPassword()
                .equals(user.getPassword());
    }
}

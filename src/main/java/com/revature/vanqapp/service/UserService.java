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

    /**
     * Takes a user persists it then returns the user
     * @param user The user that is going to be created
     * @return the full user object that was persisted is returned.
     */
    public User createUser(User user){
        return userRepository.save(user);
    }

    /**
     * Takes an integer userId then returns a User
     * @param userId the userId used to look up the user
     * @return Either returns a user or returns a null object if no user was found
     */
    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Takes a user and returns a boolean if the username AND password matched what is in the database
     * @param user the User to check against the persisted database
     * @return True if the user is in the system, false otherwise
     * @throws InvalidCredentialsException Throws an error if the username does not exist
     */
    public boolean validate(User user) throws InvalidCredentialsException {
        return userRepository.findByUsername(user.getUsername()).orElseThrow(InvalidCredentialsException::new)
                .getPassword()
                .equals(user.getPassword());
    }
}

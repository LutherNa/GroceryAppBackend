package com.revature.vanqapp.service;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.UserAuthRequest;
import com.revature.vanqapp.repository.UserRepository;
import com.revature.vanqapp.util.JwtUtil;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public User createUser(User user) {
        return createUser(new UserAuthRequest(user.getUsername(),user.getPassword()));
    }

    /**
     * Takes a user persists it then returns the user
     * @param userAuthRequest The Auth Request corresponding to the user that is going to be created
     * @return the full user object that was persisted is returned.
     */
    public User createUser(UserAuthRequest userAuthRequest) throws IllegalArgumentException {
        if (userRepository.existsByUsername(userAuthRequest.getUsername()) || userAuthRequest.getUsername() == null) {
            throw new IllegalArgumentException("Username Not Valid");
        }
        else {
            User user = new User();
            user.setUsername(userAuthRequest.getUsername());
            user.setPassword(userAuthRequest.getPassword());
            return userRepository.save(user);
        }
    }

    /**
     * Takes an integer userId then returns a User
     * @param userId the userId used to look up the user
     * @return Either returns a user or returns a null object if no user was found
     */
    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User findByToken(String token) throws Exception {
        Optional<User> user = userRepository.findByUsername(jwtUtil.extractUsername(token));
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new Exception("Token Does Not Correspond to User");
        }
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

    /** implementation of UserDetailsService method for Spring Security.
     *
     * @param username Username expected to be in database.
     * @return User object from database.
     * @throws UsernameNotFoundException Throws exception on empty optional from repository.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        else {
//            This feels ugly. - NL
            throw new UsernameNotFoundException("Username Not Found");
        }
    }
}

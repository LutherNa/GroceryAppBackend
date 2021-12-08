package com.revature.vanqapp.service;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.UserRepository;
import org.apache.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private User mockUser;

    @InjectMocks
    private UserService userService;

    User user;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        doReturn(mockUser).when(mockUserRepository).save(mockUser);
        ReflectionTestUtils.setField(
                userService,
                "userRepository",
                mockUserRepository);
        user = new User();
        user.setUserId(0);
        user.setUsername("testname");
        user.setPassword("testpassword");
    }

    @Test
    void createUser() {
        Mockito.when(mockUserRepository.save(user)).thenReturn(user);
        User verifyUser = userService.createUser(user);
        assert ((verifyUser.getUsername() == user.getUsername()) &&
                verifyUser.getPassword() == user.getPassword());
        Mockito.verify(mockUserRepository).save(user);
    }

    @Test
    void findUserById() {
        Mockito.when(mockUserRepository.findById(0)).thenReturn(java.util.Optional.ofNullable(user));
        User findUser = userService.findUserById(0);
        assert ((findUser.getUsername() == user.getUsername()) && findUser.getPassword() == user.getPassword());
    }

    @Test
    void validate() throws InvalidCredentialsException {
        Mockito.when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.ofNullable(user));
        assert userService.validate(user);
    }
}
package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.repository.KrogerApiRepository;
import com.revature.vanqapp.repository.UserRepository;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    public static GenericObjectPool<AuthToken> tokenPool;
    @Mock
    GroceryListRepository mockGroceryListRepository;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    ProductService mockProductService;
    @Mock
    User mockUser;
    @Mock
    GroceryListService mockGroceryListService;
    @Mock
    KrogerApiRepository mockKrogerApiRepository;
    LocationService locationService;

    @BeforeEach
    public void setup() throws IOException {
        locationService = new LocationService(tokenPool);
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(
                locationService,
                "krogerApiRepository",
                mockKrogerApiRepository);
    }

    @Test
    void getLocations() {
    }
}
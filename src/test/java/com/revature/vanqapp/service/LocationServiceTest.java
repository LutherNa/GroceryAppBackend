package com.revature.vanqapp.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.*;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.repository.KrogerApiRepository;
import com.revature.vanqapp.repository.UserRepository;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
    HashMap<LocationFilterTerms, String> idSearchMap;
    HashMap<LocationFilterTerms, String> zipSearchMap;

    @BeforeEach
    public void setup() throws IOException {
//        locationService = new LocationService(tokenPool);
        locationService = new LocationService();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(
                locationService,
                "krogerApiRepository",
                mockKrogerApiRepository);
        idSearchMap = new HashMap<>();
        zipSearchMap = new HashMap<>();
        idSearchMap.put(LocationFilterTerms.locationId,"01203102301");
        zipSearchMap.put(LocationFilterTerms.zipCode,"22222");
    }

    @Test
    void testGetLocationsId() throws IOException {
        ArrayNode arrayNode = null;
        Mockito.when(mockKrogerApiRepository.krogerAPIRequest("testtesttesttest")).thenReturn(arrayNode);
        List<Location> locations = locationService.getLocations(idSearchMap);
        assert locations != null;
    }

    @Test
    void testGetLocationsZip() throws IOException {
        ArrayNode arrayNode = null;
        Mockito.when(mockKrogerApiRepository.krogerAPIRequest("testtesttesttest")).thenReturn(arrayNode);
        List<Location> locations = locationService.getLocations(zipSearchMap);
        assert locations != null;
    }
}
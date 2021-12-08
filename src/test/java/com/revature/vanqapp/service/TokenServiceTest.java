package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.revature.vanqapp.service.TokenService.getToken;
import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getTestToken() throws IOException {
        AuthToken authToken = getToken();
        assert authToken != null;
    }
}
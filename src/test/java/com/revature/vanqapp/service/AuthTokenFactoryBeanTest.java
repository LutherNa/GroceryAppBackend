package com.revature.vanqapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
class AuthTokenFactoryBeanTest {

    AuthTokenFactoryBean authTokenFactoryBean = new AuthTokenFactoryBean();


    @Test
    void getAuthTokenPool() {
        authTokenFactoryBean.getAuthTokenPool();
    }
}
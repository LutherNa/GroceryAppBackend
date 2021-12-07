package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.util.AuthTokenFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenFactoryBean {

    @Bean
    @Scope("singleton")
    public GenericObjectPool<AuthToken> getAuthTokenPool() {
        return new GenericObjectPool<>(new AuthTokenFactory());
    }

}

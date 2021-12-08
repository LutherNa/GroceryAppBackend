package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.util.AuthTokenFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenFactoryBean {

    @Bean
    @Scope("singleton")
    public GenericObjectPool<AuthToken> getAuthTokenPool() {
        GenericObjectPool<AuthToken> tokenPool = new GenericObjectPool<>(new AuthTokenFactory());
        GenericObjectPoolConfig<AuthToken> tokenPoolConfig = new GenericObjectPoolConfig<>();
        tokenPoolConfig.setMaxTotal(1);
        tokenPool.setConfig(tokenPoolConfig);
        return tokenPool;
    }

}

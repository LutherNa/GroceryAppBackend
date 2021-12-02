package com.revature.vanqapp.util;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.service.TokenService;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class AuthTokenFactory extends BasePooledObjectFactory<AuthToken> {

    @Override
    public AuthToken create() throws Exception {
        return TokenService.getToken();
    }
    /**
     * Use the default PooledObject implementation.
     */
    @Override
    public PooledObject<AuthToken> wrap(AuthToken authToken) {
        return new DefaultPooledObject<>(authToken);
    }
}

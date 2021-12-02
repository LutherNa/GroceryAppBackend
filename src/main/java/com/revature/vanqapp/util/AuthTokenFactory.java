package com.revature.vanqapp.util;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.service.TokenService;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

public class AuthTokenFactory extends BasePooledObjectFactory<AuthToken> {

    /**
     * @return Returns new token from TokenService.
     * @throws IOException Throws IOException on failure to query for token.
     */
    @Override
    public AuthToken create() throws IOException {
        return TokenService.getToken();
    }
    /**
     * Use the default PooledObject implementation.
     */
    @Override
    public PooledObject<AuthToken> wrap(AuthToken authToken) {
        return new DefaultPooledObject<>(authToken);
    }

    /**
     * @param token
     * @return Returns false if token is expired.
     */
    @Override
    public boolean validateObject(PooledObject<AuthToken> token) {
        return token.getObject().isNotTimedOut();
    }
}

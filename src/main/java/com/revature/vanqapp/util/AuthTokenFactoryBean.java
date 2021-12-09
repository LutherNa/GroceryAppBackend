package com.revature.vanqapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.vanqapp.model.AuthToken;
import com.squareup.okhttp.*;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthTokenFactoryBean {

    @Bean
    @Scope("singleton")
    public GenericObjectPool<AuthToken> getAuthTokenPool() {
        GenericObjectPool<AuthToken> tokenPool = new GenericObjectPool<>(new AuthTokenFactory());
        GenericObjectPoolConfig<AuthToken> tokenPoolConfig = new GenericObjectPoolConfig<>();
        tokenPoolConfig.setMaxTotal(10);
        tokenPool.setConfig(tokenPoolConfig);
        return tokenPool;
    }

    public static class AuthTokenFactory extends BasePooledObjectFactory<AuthToken> {

        /**
         * @return Returns new token from TokenService.
         * @throws IOException Throws IOException on failure to query for token.
         */
        @Override
        public AuthToken create() throws IOException {
            return getToken();
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

            /**
             * Creates an Authorization token
             * @return Returns a verified token
             * @throws IOException Throws IOException if unable to call readValue from mapper
             */
        private static AuthToken getToken() throws IOException {
            OkHttpClient client = new OkHttpClient();
            final ObjectMapper mapper = new ObjectMapper();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&scope=product.compact");
            Request request = new Request.Builder()
                    .url("https://api.kroger.com/v1/connect/oauth2/token")
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "Basic dmFucXVpc2hzaG9wcGluZ2FwcC1kNTkyNDM4YTZkM2EwNWQ4MmRjMWRmZDI1YTA1MmY4MjEyOTk4MjU1OTcyMDAwNDEyOTU6T2ZpRWFjOTU2dFBQajAzT3hOSER5d1YyT0pCaWFac3pBeDV0YnF5eQ==")
                    .build();

            Response response = client.newCall(request).execute();
            return mapper.readValue(response.body().string(),AuthToken.class);
        }
    }
}

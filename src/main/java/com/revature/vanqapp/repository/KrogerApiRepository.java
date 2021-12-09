package com.revature.vanqapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.util.AuthTokenFactoryBean;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KrogerApiRepository {

    @Autowired
    private AuthTokenFactoryBean pool;
    private ObjectPool<AuthToken> tokenPool;

//    public KrogerApiRepository(ObjectPool<AuthToken> pool) {
//        this.tokenPool = pool;
//    }

    /**
     * Takes a stringURL and queries the KrogerAPI
     * @param url the url used to query the API
     * @return returns an ArrayNode of all the Json objects collected
     */
    public ArrayNode krogerAPIRequest(String url){
        if (tokenPool == null) {
            this.tokenPool = pool.getAuthTokenPool();
        }
        AuthToken authToken = null;
        ArrayNode arrayNode = null;
        try {
            authToken = tokenPool.borrowObject();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + authToken.getAccess_token())
                    .build();
            Response response = client.newCall(request).execute();
            //needs error handling for empty ArrayNode
            arrayNode = (ArrayNode) new ObjectMapper().readTree(response.body().string()).path("data");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != authToken) {
                    tokenPool.returnObject(authToken);
                }
            } catch (Exception e) {
                // ignored
            }
        }
        return arrayNode;
    }
}

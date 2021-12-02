package com.revature.vanqapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.pool2.ObjectPool;

public class KrogerApiRepository {

    private ObjectPool<AuthToken> tokenPool;

    public KrogerApiRepository(ObjectPool<AuthToken> pool) {
        this.tokenPool = pool;
    }


    public ArrayNode krogerAPIRequest(String url){
        AuthToken authToken;
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
            arrayNode = (ArrayNode) new ObjectMapper().readTree(response.body().string()).path("data");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return arrayNode;
        }
    }
}

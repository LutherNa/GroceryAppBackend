package com.revature.vanqapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.vanqapp.model.AuthToken;
import com.squareup.okhttp.*;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class TokenService {

    /**
     * Creates an Authorization token
     * @return
     * @throws IOException
     */
    public static AuthToken getToken() throws IOException {
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

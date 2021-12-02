package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.*;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationService {
    AuthToken authToken;

    public LocationService() throws IOException {
        authToken = TokenService.getToken();
    }
    /**
     * Takes a hashmap of (FilterTerms,String) and returns a list of mapped Products in a list, can be an empty list
     * @param searchMap Parameters for the search
     * @return a list of Products or an empty list
     * @throws IOException throws IOException because getAPISearchResult throws an IOException
     */
    public List<Location> getLocations(HashMap<LocationFilterTerms,String> searchMap) throws IOException {
        return parseArrayNodeToLocation(getAPISearchLocation(searchMap));
    }

    /**
     * Gets location based
     * @param searchMap
     * @return
     * @throws IOException
     */

    private ArrayNode getAPISearchLocation(HashMap<LocationFilterTerms, String> searchMap) throws IOException {
        StringBuilder searchBuilder = new StringBuilder().append("https://api.kroger.com/v1/locations?");
        for (LocationFilterTerms term : searchMap.keySet()) {
            searchBuilder.append("filter." + term);
            switch (term) {
                case zipCode:
                case lat:
                case lon:
                case latLong:
                    searchBuilder.append(".near");
                    break;
            }
            searchBuilder.append("=" + searchMap.get(term) + "&");
        }
        searchBuilder.setLength(searchBuilder.length()-1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(searchBuilder.toString())
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + authToken.getAccess_token())
                .build();
        Response response = client.newCall(request).execute();
        return (ArrayNode) new ObjectMapper().readTree(response.body().string()).path("data");
    }


    private List<Location> parseArrayNodeToLocation(ArrayNode arrayNode) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("LocationDeserializer");
        module.addDeserializer(Location.class, new LocationDeserializer(Location.class));
        mapper.registerModule(module);
        List<Location> locations = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode objNode : arrayNode) {
                Location location = mapper.readValue(objNode.toString(), Location.class);
                locations.add(location);
            }
        }
        return locations;
    }
}

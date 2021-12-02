package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.*;
import com.revature.vanqapp.repository.KrogerApiRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.pool2.ObjectPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationService {
    private ObjectPool<AuthToken> tokenPool;
    KrogerApiRepository krogerApiRepository;

    public LocationService(ObjectPool<AuthToken> pool) {
        this.tokenPool = pool;
        krogerApiRepository = new KrogerApiRepository(this.tokenPool);
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
        return krogerApiRepository.krogerAPIRequest(searchBuilder.toString());
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

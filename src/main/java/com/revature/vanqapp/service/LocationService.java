package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.*;
import com.revature.vanqapp.repository.KrogerApiRepository;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationService {

    @Autowired
    KrogerApiRepository krogerApiRepository;

    public LocationService(ObjectPool<AuthToken> pool) {
        krogerApiRepository = new KrogerApiRepository(pool);
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
     * @param searchMap the Location term and information to search for in a pair
     * @return returns a url string
     */

    private ArrayNode getAPISearchLocation(HashMap<LocationFilterTerms, String> searchMap){
        StringBuilder searchBuilder = new StringBuilder().append("https://api.kroger.com/v1/locations?");
        for (LocationFilterTerms term : searchMap.keySet()) {
            searchBuilder.append("filter.").append(term);
            switch (term) {
                case zipCode:
                case lat:
                case lon:
                case latLong:
                    searchBuilder.append(".near");
                    break;
            }
            searchBuilder.append("=").append(searchMap.get(term)).append("&");
        }
        searchBuilder.setLength(searchBuilder.length()-1);
        return krogerApiRepository.krogerAPIRequest(searchBuilder.toString());
    }


    private List<Location> parseArrayNodeToLocation(ArrayNode arrayNode) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule("LocationDeserializer");
//        module.addDeserializer(Location.class, new LocationDeserializer(Location.class));
//        mapper.registerModule(module);
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

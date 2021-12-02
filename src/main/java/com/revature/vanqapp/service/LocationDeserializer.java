package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.revature.vanqapp.model.Location;
import com.revature.vanqapp.model.Product;

import java.io.IOException;

public class LocationDeserializer extends StdDeserializer<Location> {

    /* ignoring {"upc", "aisleLocation", "categories", "countryOrigin", "images", "items", "temperature"}

     */

    /**
     *
     * @param vc
     */
    public LocationDeserializer(Class<?> vc){
        super(vc);
    }

    /**
     * Takes a JsonParser and DeserializationContext and returns a Location. This just defines how to get
     * a Location from a Json object
     * @param parser the Json which holds the information
     * @param deserializer ???
     * @return returns a Mapped product
     * @throws IOException throws an IOException if unable to getValueAsString
     */
    @Override
    public Location deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        Location location = new Location();
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                if("addressLine1".equals(fieldName)){
                    location.setAddressLine1(parser.getValueAsString());
                }else if ("city".equals(fieldName)){
                    location.setCity(parser.getValueAsString());
                }else if ("state".equals(fieldName)){
                    location.setState(parser.getValueAsString());
                }else if ("zipCode".equals(fieldName)){
                    location.setZipCode(Integer.parseInt(parser.getValueAsString()));
                }else if ("phone".equals(fieldName)){
                    location.setPhone(parser.getValueAsString());
                }else if ("locationId".equals(fieldName)){
                    location.setLocationId(parser.getValueAsString());
                }else if ("name".equals(fieldName)){
                    location.setName(parser.getValueAsString());
                }
            }
        }
        return location;
    }
}
/*

     private String locationId;
     private String name;
 */

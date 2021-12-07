package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.revature.vanqapp.model.product.Product;

import java.io.IOException;

public class ProductDeserializer extends StdDeserializer<Product> {

    /* ignoring {"upc", "aisleLocation", "categories", "countryOrigin", "images", "items", "temperature"}
     */

    /**
     * @param vc
     */
    public ProductDeserializer(Class<?> vc){
        super(vc);
    }

    /**
     * Takes a JsonParser and DeserializationContext and returns a Product. This just defines how to get
     * a Product from a Json object
     * @param parser the Json which holds the information
     * @param deserializer ???
     * @return returns a Mapped product
     * @throws IOException throws an IOException if unable to getValueAsString
     */
    @Override
    public Product deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        Product product = new Product();
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                if("productId".equals(fieldName)){
                    product.setProductId(parser.getValueAsString());
                }else if ("brand".equals(fieldName)){
                    product.setBrand(parser.getValueAsString());
                }else if ("description".equals(fieldName)){
                    product.setDescription(parser.getValueAsString());
                }else if ("upc".equals(fieldName)){
                    product.setUPC(parser.getValueAsString());
                }
            }
        }
        return product;
    }
}

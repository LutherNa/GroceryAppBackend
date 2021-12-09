package com.revature.vanqapp.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.repository.KrogerApiRepository;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ProductServiceTest {

    HashMap<ProductFilterTerms,String> searchMap;
    HashMap<ProductFilterTerms,String> wrongSearchMap;
    ArrayList<ProductFilterTerms> productFilterTerms;
    public static GenericObjectPool<AuthToken> tokenPool;
    ProductService productService;
    @Mock
    KrogerApiRepository mockKrogerApiRepository;
    @Mock
    ProductService mockProductService;
    @Mock
    ArrayNode arrayNode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
//        productService = new ProductService(tokenPool);
        productService = new ProductService();
        ReflectionTestUtils.setField(
                productService,
                "krogerApiRepository",
                mockKrogerApiRepository);
        ReflectionTestUtils.setField(
                mockProductService,
                "krogerApiRepository",
                mockKrogerApiRepository);
        searchMap = new HashMap<>();
        wrongSearchMap = new HashMap<>();
        productFilterTerms = new ArrayList<>();
        wrongSearchMap.put(ProductFilterTerms.productId,"01203102301");
        wrongSearchMap.put(ProductFilterTerms.locationId,"91293189230");
        wrongSearchMap.put(ProductFilterTerms.brand,"Kellogg's");
        searchMap.put(ProductFilterTerms.productId,"01203102301");
        searchMap.put(ProductFilterTerms.locationId,"91293189230");
        productFilterTerms.add(ProductFilterTerms.productId);
        productFilterTerms.add(ProductFilterTerms.locationId);
    }

    @Test
    void testGetProductsByIdAndLocation() throws IOException, NoSuchMethodException {
        List<Product> productList = productService.getProductsByIdAndLocation(searchMap);
        assert productList != null;
    }

    @Test
    void testFailGetProductsBySpecified() throws IOException {
        productService.getProductsBySpecified(wrongSearchMap,productFilterTerms);
    }

    @Test
    void testGetProductsBySpecified() throws IOException {
        List<Product> productList = productService.getProductsBySpecified(searchMap,productFilterTerms);
        assert productList != null;
    }

    @Test
    void testGetProducts() throws IOException {
        List<Product> productList = productService.getProducts(searchMap);
        assert productList != null;
    }

    @Test
    void testVerifyProductById() throws IOException {
        boolean verified = productService.verifyProductById("0");
        assert verified == false;
    }
}
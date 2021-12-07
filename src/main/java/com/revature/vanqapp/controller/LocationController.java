package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.Location;
import com.revature.vanqapp.model.LocationFilterTerms;
import com.revature.vanqapp.service.LocationService;
import com.revature.vanqapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;

@RestController()
@RequestMapping(value = "/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

//    public LocationController(){
//        try{
//            locationService = new LocationService(tokenPool);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping
    public List<Location> getLocationByZip(@RequestBody HashMap<LocationFilterTerms, String> search) throws IOException {
        /*
        HashMap<LocationFilterTerms, String> search = new HashMap<>();
        search.put(LocationFilterTerms.zipCode,zipCode.toString());
         */
        return locationService.getLocations(search);
    }

    @PostMapping("/{locationId}")
    public  List<Location> getLocationById(@PathVariable String locationId) throws IOException {
        HashMap<LocationFilterTerms, String> search = new HashMap<>();
        search.put(LocationFilterTerms.locationId, locationId);
        return locationService.getLocations(search);
    }
}

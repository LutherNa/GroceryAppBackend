package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.Location;
import com.revature.vanqapp.model.LocationFilterTerms;
import com.revature.vanqapp.service.LocationService;
import com.revature.vanqapp.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping(value = "/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public List<Location> getLocationByZip(
            @Parameter(schema = @Schema(implementation = LocationFilterTerms.LocationFilterTermsDummy.class))
            @RequestBody HashMap<LocationFilterTerms, String> search) throws IOException {
        return locationService.getLocations(search);
    }

    @PostMapping("/{locationId}")
    public  List<Location> getLocationById(@PathVariable String locationId) throws IOException {
        HashMap<LocationFilterTerms, String> search = new HashMap<>();
        search.put(LocationFilterTerms.locationId, locationId);
        return locationService.getLocations(search);
    }
}

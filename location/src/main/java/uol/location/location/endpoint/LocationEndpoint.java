package uol.location.location.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import uol.location.location.application.LocationApplication;
import uol.location.location.endpoint.resource.LocationEndpointBody;
import uol.location.location.domain.Location;

import static uol.location.location.converter.LocationConverter.*;
import static uol.location.location.converter.WeatherConverter.*;

@RestController
public class LocationEndpoint {
	
	private final LocationApplication locationApplication;
	
	public LocationEndpoint(LocationApplication locationApplication) {
        this.locationApplication = locationApplication;
    }

    @GetMapping("/locations/{id}")
    ResponseEntity getById(@PathVariable(value="id") Long id) {
        Location location = locationApplication.getById(id);
        if (!ObjectUtils.isEmpty(location)) {
            LocationEndpointBody locationEndpointBody = toLocationEndpointBody(location);
            locationEndpointBody.setWeather(toWeatherEndpointBody(location.getWeather()));
            return ResponseEntity.status(HttpStatus.OK).body(locationEndpointBody);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

package uol.location.location.endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import uol.location.location.application.LocationApplication;
import uol.location.location.endpoint.objects.LocationEnpointBody;
import uol.location.location.objects.Location;

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
            LocationEnpointBody locationEnpointBody = new LocationEnpointBody();
            BeanUtils.copyProperties(location, locationEnpointBody);
            BeanUtils.copyProperties(location.getWeather(), locationEnpointBody.getWeather());
            return ResponseEntity.status(HttpStatus.OK).body(locationEnpointBody);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/locations")
    ResponseEntity create(@RequestBody String ip) {
        Location location = locationApplication.create(ip);
        LocationEnpointBody locationEnpointBody = new LocationEnpointBody();
        BeanUtils.copyProperties(location, locationEnpointBody);
        BeanUtils.copyProperties(location.getWeather(), locationEnpointBody.getWeather());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationEnpointBody);
    }

    @DeleteMapping("/locations/{id}")
    ResponseEntity deleteById(@PathVariable(value="id") Long id) {
        boolean isDeleted = locationApplication.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

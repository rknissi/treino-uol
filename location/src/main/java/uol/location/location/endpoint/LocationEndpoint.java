package uol.location.location.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import uol.location.location.application.LocationApplication;
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
            return ResponseEntity.status(HttpStatus.OK).body(location);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/locations")
    ResponseEntity create(@RequestBody String ip) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationApplication.create(ip));
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

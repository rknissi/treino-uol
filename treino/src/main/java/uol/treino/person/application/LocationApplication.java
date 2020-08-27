package uol.treino.person.application;

import org.springframework.stereotype.Component;
import uol.treino.person.gateway.LocationGateway;
import uol.treino.person.domain.Location;
import uol.treino.person.gateway.response.LocationGatewayResponse;

import static uol.treino.person.converter.LocationConverter.toLocation;

@Component
public class LocationApplication {

    private final LocationGateway locationGateway;

    public LocationApplication(LocationGateway locationGateway) {
        this.locationGateway = locationGateway;
    }

    public Location getById(Long id) {
        LocationGatewayResponse locationGatewayResponse =  locationGateway.getById(id);
        if (locationGatewayResponse != null) {
            return toLocation(locationGatewayResponse);
        }
        return null;
    }

    public void deleteByid(Long id) {
        locationGateway.delete(id);
    }
}

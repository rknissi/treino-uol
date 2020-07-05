package uol.treino.person.application;

import org.springframework.stereotype.Component;
import uol.treino.person.gateway.LocationGateway;
import uol.treino.person.gateway.objects.LocationGatewayResponse;
import uol.treino.person.objects.Location;

@Component
public class LocationApplication {

    private final LocationGateway locationGateway;

    public LocationApplication(LocationGateway locationGateway) {
        this.locationGateway = locationGateway;
    }

    public Location getById(Long id) {
        return locationGateway.getById(id);
    }

    public Location create(String ip) {
        return locationGateway.create(ip);
    }

    public void deleteByid(Long id) {
        locationGateway.delete(id);
    }
}

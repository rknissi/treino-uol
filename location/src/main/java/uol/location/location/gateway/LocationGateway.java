package uol.location.location.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.location.location.objects.Location;
import uol.location.location.gateway.objects.LocationGatewayResponse;

@Component
public class LocationGateway {
    public Location getGeographicalLocation(String ipv4) {
        ipv4 = "177.140.72.58";
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.getForObject("https://ipvigilante.com/" + ipv4, LocationGatewayResponse.class);
        return locationGatewayResponse.getData();
    }
}

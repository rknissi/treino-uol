package uol.location.location.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uol.location.location.domain.Location;
import uol.location.location.gateway.response.LocationGatewayResponse;

@Configuration
public class LocationGateway {

    @Value("${ipvigilante.url}")
    String ipvigilanteUrl;

    public Location getGeographicalLocation(String ipv4) {
        ipv4 = "177.140.72.58";
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.getForObject(ipvigilanteUrl + ipv4, LocationGatewayResponse.class);
        return locationGatewayResponse.getData();
    }
}

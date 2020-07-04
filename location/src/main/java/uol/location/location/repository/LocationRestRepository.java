package uol.location.location.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.location.location.objects.Location;
import uol.location.location.objects.LocationResponse;

@Component
public class LocationRestRepository {
    public Location getGeographicalLocation(String ipv4) {
        ipv4 = "177.140.72.58";
        RestTemplate restTemplate = new RestTemplate();
        LocationResponse locationResponse = restTemplate.getForObject("https://ipvigilante.com/" + ipv4, LocationResponse.class);
        return locationResponse.getData();
    }
}

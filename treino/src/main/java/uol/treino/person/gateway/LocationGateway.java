package uol.treino.person.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.treino.person.objects.Location;

@Component
public class LocationGateway {
    public Location getById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = restTemplate.getForObject("http://localhost:8081/locations/" + id, Location.class);
        return location;
    }

    public Location create(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = restTemplate.postForObject("http://localhost:8081/locations/", ip, Location.class);
        return location;
    }

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/locations/" + id, Location.class);
    }
}

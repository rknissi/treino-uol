package uol.treino.person.gateway;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.treino.person.gateway.objects.LocationGatewayResponse;
import uol.treino.person.objects.Location;
import uol.treino.person.objects.Weather;

@Component
public class LocationGateway {
    public Location getById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.getForObject("http://localhost:8081/locations/" + id, LocationGatewayResponse.class);
        Location location = new Location();
        BeanUtils.copyProperties(locationGatewayResponse, location);
        location.setWeather(new Weather());
        BeanUtils.copyProperties(locationGatewayResponse.getWeather(), location.getWeather());
        return location;
    }

    public Location create(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.postForObject("http://localhost:8081/locations/", ip, LocationGatewayResponse.class);
        Location location = new Location();
        BeanUtils.copyProperties(locationGatewayResponse, location);
        location.setWeather(new Weather());
        BeanUtils.copyProperties(locationGatewayResponse.getWeather(), location.getWeather());
        return location;
    }

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/locations/" + id, LocationGatewayResponse.class);
    }
}

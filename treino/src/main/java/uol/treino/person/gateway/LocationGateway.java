package uol.treino.person.gateway;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uol.treino.person.gateway.response.LocationGatewayResponse;
import uol.treino.person.dto.Location;
import uol.treino.person.dto.Weather;

@Configuration
public class LocationGateway {

    @Value("${url.location}")
    String locationUrl;

    public Location getById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.getForObject( locationUrl + id, LocationGatewayResponse.class);
        Location location = new Location();
        BeanUtils.copyProperties(locationGatewayResponse, location);
        location.setWeather(new Weather());
        BeanUtils.copyProperties(locationGatewayResponse.getWeather(), location.getWeather());
        return location;
    }

    public Location create(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        LocationGatewayResponse locationGatewayResponse = restTemplate.postForObject(locationUrl, ip, LocationGatewayResponse.class);
        Location location = new Location();
        BeanUtils.copyProperties(locationGatewayResponse, location);
        location.setWeather(new Weather());
        BeanUtils.copyProperties(locationGatewayResponse.getWeather(), location.getWeather());
        return location;
    }

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(locationUrl + id, LocationGatewayResponse.class);
    }
}

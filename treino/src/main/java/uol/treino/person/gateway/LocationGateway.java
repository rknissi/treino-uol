package uol.treino.person.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uol.treino.person.converter.LocationConverter;
import uol.treino.person.gateway.response.LocationGatewayResponse;
import uol.treino.person.dto.Location;

import static uol.treino.person.converter.LocationConverter.*;

@Configuration
public class LocationGateway {

    @Value("${url.location}")
    String locationUrl;

    public LocationGatewayResponse getById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject( locationUrl + id, LocationGatewayResponse.class);
    }

    public LocationGatewayResponse create(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(locationUrl, ip, LocationGatewayResponse.class);
    }

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(locationUrl + id, LocationGatewayResponse.class);
    }
}

package uol.treino.person.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uol.treino.person.gateway.response.LocationGatewayResponse;

@Configuration
public class LocationGateway {

    @Value("${url.location}")
    String locationUrl;

    public LocationGatewayResponse getById(Long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject( locationUrl + id, LocationGatewayResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }
            throw e;
        }
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

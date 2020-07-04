package uol.location.location.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.location.location.objects.Weather;
import uol.location.location.gateway.objects.WeatherGatewayResponse;
import uol.location.location.gateway.objects.WeatherGatewayWoeId;

@Component
public class WeatherGateway {
    public Long getWoeId(String lat, String log) {
        RestTemplate restTemplate = new RestTemplate();
        WeatherGatewayWoeId[] weatherGatewayWoeIdList =
                restTemplate
                        .getForObject("https://www.metaweather.com/api/location/search/?lattlong="
                                        + lat
                                        + ","
                                        + log,
                                WeatherGatewayWoeId[].class);

        return weatherGatewayWoeIdList[0].getWoeid().longValue();
    }

    public Weather getWeatherTemps(Long whereOnEarthId) {
        RestTemplate restTemplate = new RestTemplate();
        WeatherGatewayResponse weatherLocationResponseList =
                restTemplate
                        .getForObject("https://www.metaweather.com/api/location/"
                                    + whereOnEarthId,
                                WeatherGatewayResponse.class);

        return weatherLocationResponseList.getWeatherList().get(0);
    }
}

package uol.location.location.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.location.location.objects.Weather;
import uol.location.location.gateway.objects.WeatherGatewayResponse;
import uol.location.location.gateway.objects.WeatherGatewayWoeId;

@Configuration
public class WeatherGateway {

    @Value("${metaweather.url}")
    String metaweatherUrl;

    public Long getWoeId(String lat, String log) {
        RestTemplate restTemplate = new RestTemplate();
        WeatherGatewayWoeId[] weatherGatewayWoeIdList =
                restTemplate
                        .getForObject(metaweatherUrl +
                                        "search/?lattlong="
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
                        .getForObject(metaweatherUrl
                                    + whereOnEarthId,
                                WeatherGatewayResponse.class);

        return weatherLocationResponseList.getWeatherList().get(0);
    }
}

package uol.location.location.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uol.location.location.objects.Weather;
import uol.location.location.objects.WeatherList;
import uol.location.location.objects.WeatherLocation;

@Component
public class WeatherRestRepository {
    public Long getWeatherId(String lat, String log) {
        RestTemplate restTemplate = new RestTemplate();
        WeatherLocation[] weatherLocationList =
                restTemplate
                        .getForObject("https://www.metaweather.com/api/location/search/?lattlong="
                                        + lat
                                        + ","
                                        + log,
                                WeatherLocation[].class);

        return weatherLocationList[0].getWoeid().longValue();
    }

    public Weather getWeatherTemps(Long whereOnEarthId) {
        RestTemplate restTemplate = new RestTemplate();
        WeatherList weatherLocationResponseList =
                restTemplate
                        .getForObject("https://www.metaweather.com/api/location/"
                                    + whereOnEarthId,
                                WeatherList.class);

        return weatherLocationResponseList.getWeatherList().get(0);
    }
}

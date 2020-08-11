package uol.location.location.application;

import org.springframework.stereotype.Component;
import uol.location.location.dto.Weather;
import uol.location.location.gateway.WeatherGateway;

@Component
public class WeatherApplication {
    private final WeatherGateway weatherGateway;

    public WeatherApplication(WeatherGateway weatherGateway) {
        this.weatherGateway = weatherGateway;
    }

    public Weather getWeatherFromLatLog(String lat, String log) {
        return getWeatherTemp(getWhereOnEarthId(lat, log));
    }

    private Long getWhereOnEarthId(String lat, String log) {
        return weatherGateway.getWoeId(lat, log);
    }

    private Weather getWeatherTemp(Long whereOnEarthId) {
        return weatherGateway.getWeatherTemps(whereOnEarthId);
    }

}

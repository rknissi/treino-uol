package uol.location.location.application;

import org.springframework.stereotype.Component;
import uol.location.location.objects.Weather;
import uol.location.location.repository.WeatherRestRepository;

@Component
public class WeatherApplication {
    private final WeatherRestRepository weatherRestRepository;

    public WeatherApplication(WeatherRestRepository weatherRestRepository) {
        this.weatherRestRepository = weatherRestRepository;
    }

    public Weather getWeatherFromLatLog(String lat, String log) {
        return getWeatherTemp(getWhereOnEarthId(lat, log));
    }

    private Long getWhereOnEarthId(String lat, String log) {
        return weatherRestRepository.getWeatherId(lat, log);
    }

    private Weather getWeatherTemp(Long whereOnEarthId) {
        return weatherRestRepository.getWeatherTemps(whereOnEarthId);
    }

}

package uol.location.location.converter;

import uol.location.location.dto.Weather;
import uol.location.location.endpoint.resource.WeatherEndpointBody;
import uol.location.location.repository.entity.WeatherRepositoryEntity;

public class WeatherConverter {

    public static WeatherRepositoryEntity toWeatherRepositoryEntity(Weather weather) {
        WeatherRepositoryEntity weatherRepositoryEntity = new WeatherRepositoryEntity();

        weatherRepositoryEntity.setId(weather.getId());
        weatherRepositoryEntity.setMaxTemp(weather.getMaxTemp());
        weatherRepositoryEntity.setMinTemp(weather.getMinTemp());

        return weatherRepositoryEntity;
    }

    public static Weather toWeather(WeatherRepositoryEntity weatherRepositoryEntity) {
        Weather weather = new Weather();

        weather.setId(weatherRepositoryEntity.getId());
        weather.setMaxTemp(weatherRepositoryEntity.getMaxTemp());
        weather.setMinTemp(weatherRepositoryEntity.getMinTemp());

        return weather;
    }

    public static WeatherEndpointBody toWeatherEndpointBody(Weather weather) {
        WeatherEndpointBody weatherEndpointBody = new WeatherEndpointBody();

        weatherEndpointBody.setId(weather.getId());
        weatherEndpointBody.setMaxTemp(weather.getMaxTemp());
        weatherEndpointBody.setMinTemp(weather.getMinTemp());

        return weatherEndpointBody;
    }
}

package uol.treino.person.converter;

import uol.treino.person.dto.Location;
import uol.treino.person.dto.Weather;
import uol.treino.person.gateway.response.LocationGatewayResponse;
import uol.treino.person.gateway.response.WeatherGatewayResponse;

public class LocationConverter {

    public static Location toLocation(LocationGatewayResponse locationGatewayResponse) {
        Location location = new Location();

        location.setId(locationGatewayResponse.getId());
        location.setCountry(locationGatewayResponse.getCountry());
        location.setCity(locationGatewayResponse.getCity());
        location.setLatitude(locationGatewayResponse.getLatitude());
        location.setLongitude(locationGatewayResponse.getLongitude());

        location.setWeather(toWeather(locationGatewayResponse.getWeather()));

        return location;
    }

    public static Weather toWeather(WeatherGatewayResponse weatherGatewayResponse) {
        Weather weather = new Weather();

        weather.setId(weatherGatewayResponse.getId());
        weather.setMaxTemp(weatherGatewayResponse.getMaxTemp());
        weather.setMinTemp(weatherGatewayResponse.getMinTemp());

        return weather;
    }
}

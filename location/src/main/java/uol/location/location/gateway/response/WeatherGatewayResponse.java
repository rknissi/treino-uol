package uol.location.location.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import uol.location.location.domain.Weather;

import java.util.List;

public class WeatherGatewayResponse {
    @JsonProperty("consolidated_weather")
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}

package uol.location.location.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherList {
    @JsonProperty("consolidated_weather")
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}

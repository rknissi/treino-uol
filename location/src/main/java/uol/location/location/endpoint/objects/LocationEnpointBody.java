package uol.location.location.endpoint.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import uol.location.location.objects.Weather;

import java.io.Serializable;

@JsonRootName(value = "data")
public class LocationEnpointBody implements  Serializable{

    public LocationEnpointBody() {
        this.setWeather(new WeatherEndpointBody());
    }

    private Long id;

    private String continent;
	
    private String country;
	
    private String subdivision1;
	
    private String subdivision2;
	
    private String city;

    private String latitude;

    private String longitude;

    private WeatherEndpointBody weather;

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSubdivision1() {
        return subdivision1;
    }

    public void setSubdivision1(String subdivision1) {
        this.subdivision1 = subdivision1;
    }

    public String getSubdivision2() {
        return subdivision2;
    }

    public void setSubdivision2(String subdivision2) {
        this.subdivision2 = subdivision2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public WeatherEndpointBody getWeather() {
        return weather;
    }

    public void setWeather(WeatherEndpointBody weather) {
        this.weather = weather;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


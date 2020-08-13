package uol.treino.person.gateway.response;

public class LocationGatewayResponse {
    private Long id;

    private String country;

    private String city;

    private String latitude;
    private String longitude;
    private WeatherGatewayResponse weather;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public WeatherGatewayResponse getWeather() {
        return weather;
    }

    public void setWeather(WeatherGatewayResponse weather) {
        this.weather = weather;
    }
}

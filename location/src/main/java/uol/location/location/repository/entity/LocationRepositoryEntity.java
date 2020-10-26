package uol.location.location.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class LocationRepositoryEntity {
    @Id
    @SequenceGenerator(name="location_generator",sequenceName="location_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="location_generator")
    private Long id;

    @Column(name="continent")
    private String continent;

    @Column(name="country")
    private String country;

    @Column(name="subdivision1")
    private String subdivision1;

    @Column(name="subdivision2")
    private String subdivision2;

    @Column(name="city")
    private String city;

    @Column(name="latitude")
    private String latitude;

    @Column(name="longitude")
    private String longitude;

    @Column(name="person_id")
    private Long personId;

    @Column(name="valid")
    private boolean valid;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_id", referencedColumnName = "id")
    private WeatherRepositoryEntity weather;

    public LocationRepositoryEntity() {
        this.valid = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public WeatherRepositoryEntity getWeather() {
        return weather;
    }

    public void setWeather(WeatherRepositoryEntity weather) {
        this.weather = weather;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

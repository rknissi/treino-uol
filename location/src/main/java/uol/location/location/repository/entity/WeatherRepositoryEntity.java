package uol.location.location.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "weather")
public class WeatherRepositoryEntity {

    @Id
    @SequenceGenerator(name="weather_generator",sequenceName="weather_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="weather_generator")
    private Long id;

    @Column(name="min_temp")
    private Long minTemp;

    @Column(name="max_temp")
    private Long maxTemp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Long minTemp) {
        this.minTemp = minTemp;
    }

    public Long getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Long maxTemp) {
        this.maxTemp = maxTemp;
    }

}

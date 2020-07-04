package uol.location.location.objects;

import javax.persistence.*;

@Entity
public class WeatherEntity {

    @Id
    @SequenceGenerator(name="weatherId",sequenceName="weatherId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="weatherId")
    private Long id;

    private Long minTemp;
    private Long maxTemp;

    @OneToOne
    private LocationEntity locationEntity;

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

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }
}

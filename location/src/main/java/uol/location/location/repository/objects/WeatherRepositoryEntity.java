package uol.location.location.repository.objects;

import javax.persistence.*;

@Entity
public class WeatherRepositoryEntity {

    @Id
    @SequenceGenerator(name="weatherId",sequenceName="weatherId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="weatherId")
    private Long id;

    private Long minTemp;
    private Long maxTemp;

    @OneToOne
    private LocationRepositoryEntity locationRepositoryEntity;

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

    public LocationRepositoryEntity getLocationRepositoryEntity() {
        return locationRepositoryEntity;
    }

    public void setLocationRepositoryEntity(LocationRepositoryEntity locationRepositoryEntity) {
        this.locationRepositoryEntity = locationRepositoryEntity;
    }
}

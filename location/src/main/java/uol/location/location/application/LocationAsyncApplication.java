package uol.location.location.application;

import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uol.location.location.gateway.LocationGateway;
import uol.location.location.objects.Location;
import uol.location.location.objects.Weather;
import uol.location.location.repository.LocationRepository;
import uol.location.location.repository.objects.LocationRepositoryEntity;
import uol.location.location.repository.objects.WeatherRepositoryEntity;

@Service
@EnableAsync
public class LocationAsyncApplication {

    private final LocationRepository locationRepository;
    private final LocationGateway locationGateway;
    private final WeatherApplication weatherApplication;

    public LocationAsyncApplication(LocationRepository locationRepository, LocationGateway locationGateway, WeatherApplication weatherApplication) {
        this.locationRepository = locationRepository;
        this.locationGateway = locationGateway;
        this.weatherApplication = weatherApplication;
    }
    @Async
    public void populateData(String ipv4, LocationRepositoryEntity locationRepositoryEntity) {
        Long id = locationRepositoryEntity.getId();
        Location location = locationGateway.getGeographicalLocation(ipv4);
        BeanUtils.copyProperties(location, locationRepositoryEntity);
        locationRepositoryEntity.setId(id);

        Weather weather = weatherApplication.getWeatherFromLatLog(location.getLatitude(), location.getLongitude());
        weather.setId(null);
        WeatherRepositoryEntity weatherRepositoryEntity = new WeatherRepositoryEntity();
        BeanUtils.copyProperties(weather, weatherRepositoryEntity);
        locationRepositoryEntity.setWeather(weatherRepositoryEntity);

        location.setWeather(weather);

        locationRepository.save(locationRepositoryEntity);
    }
}

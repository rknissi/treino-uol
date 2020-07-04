package uol.location.location.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uol.location.location.objects.Location;
import uol.location.location.repository.objects.LocationRepositoryEntity;
import uol.location.location.objects.Weather;
import uol.location.location.repository.objects.WeatherRepositoryEntity;
import uol.location.location.repository.LocationRepository;
import uol.location.location.gateway.LocationGateway;

import java.util.Optional;

@Service
public class LocationApplication {

	private final LocationRepository locationRepository;
	private final LocationGateway locationGateway;
	private final WeatherApplication weatherApplication;

	public LocationApplication(LocationRepository locationRepository, LocationGateway locationGateway, WeatherApplication weatherApplication) {
		this.locationRepository = locationRepository;
		this.locationGateway = locationGateway;
		this.weatherApplication = weatherApplication;
	}

	public Location create(String ipv4) {
		Location location = locationGateway.getGeographicalLocation(ipv4);
		LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
		BeanUtils.copyProperties(location, locationRepositoryEntity);

		Weather weather = weatherApplication.getWeatherFromLatLog(location.getLatitude(), location.getLongitude());
		weather.setId(null);
		WeatherRepositoryEntity weatherRepositoryEntity = new WeatherRepositoryEntity();
		BeanUtils.copyProperties(weather, weatherRepositoryEntity);
		locationRepositoryEntity.setWeatherRepositoryEntity(weatherRepositoryEntity);

		location.setWeather(weather);

		locationRepository.save(locationRepositoryEntity);
		location.setId(locationRepositoryEntity.getId());
		location.getWeather().setId(locationRepositoryEntity.getWeatherRepositoryEntity().getId());
		return location;
	}

	public Location getById(Long id) {
        Optional<LocationRepositoryEntity> locationEntity = locationRepository.findById(id);
        if (locationEntity.isPresent()) {
        	Location location = new Location();
			BeanUtils.copyProperties(locationEntity.get(), location);
			BeanUtils.copyProperties(locationEntity.get().getWeatherRepositoryEntity(), location.getWeather());
			return location;
        }
        return null;
    }

	public boolean deleteById(Long id) {
		Optional<LocationRepositoryEntity> locationEntity = locationRepository.findById(id);
		if (locationEntity.isPresent()) {
		    locationRepository.deleteById(id);
			return true;
		}
		return false;
	}




}

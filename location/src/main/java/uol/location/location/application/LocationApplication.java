package uol.location.location.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uol.location.location.gateway.LocationGateway;
import uol.location.location.dto.Location;
import uol.location.location.dto.Weather;
import uol.location.location.queue.LocationCreationProducerApplication;
import uol.location.location.repository.entity.LocationRepositoryEntity;
import uol.location.location.repository.LocationRepository;
import uol.location.location.repository.entity.WeatherRepositoryEntity;

import java.util.Optional;

@Service
public class LocationApplication {

	private final LocationRepository locationRepository;
	private final LocationCreationProducerApplication locationCreationProducerApplication;
	private final LocationGateway locationGateway;
	private final WeatherApplication weatherApplication;

	public LocationApplication(LocationRepository locationRepository, LocationCreationProducerApplication locationCreationProducerApplication, LocationGateway locationGateway, WeatherApplication weatherApplication) {
		this.locationRepository = locationRepository;
		this.locationCreationProducerApplication = locationCreationProducerApplication;
		this.locationGateway = locationGateway;
		this.weatherApplication = weatherApplication;
	}

	public Location create(String ipv4) {
	    Location location = new Location();
		LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
		locationRepository.save(locationRepositoryEntity);
		locationCreationProducerApplication.sendMessage(ipv4, locationRepositoryEntity.getId());
		BeanUtils.copyProperties(locationRepositoryEntity, location);
		return location;
	}

	public void populateData(String ipv4, Long id) {
	    LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
	    locationRepositoryEntity.setId(id);

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


	public Location getById(Long id) {
		Optional<LocationRepositoryEntity> locationEntity = locationRepository.findById(id);
        if (locationEntity.isPresent()) {
        	Location location = new Location();
			BeanUtils.copyProperties(locationEntity.get(), location);
			if (locationEntity.get().getWeather() != null) {
				BeanUtils.copyProperties(locationEntity.get().getWeather(), location.getWeather());
			}
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

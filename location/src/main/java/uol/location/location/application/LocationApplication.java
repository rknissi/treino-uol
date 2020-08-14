package uol.location.location.application;

import org.springframework.stereotype.Service;
import uol.location.location.converter.LocationConverter;
import uol.location.location.converter.WeatherConverter;
import uol.location.location.gateway.LocationGateway;
import uol.location.location.dto.Location;
import uol.location.location.dto.Weather;
import uol.location.location.queue.LocationCreationProducerApplication;
import uol.location.location.repository.entity.LocationRepositoryEntity;
import uol.location.location.repository.LocationRepository;

import java.util.Optional;

import static uol.location.location.converter.LocationConverter.*;
import static uol.location.location.converter.WeatherConverter.*;

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
		LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
		locationRepository.save(locationRepositoryEntity);
		locationCreationProducerApplication.sendMessage(ipv4, locationRepositoryEntity.getId());
		return toLocation(locationRepositoryEntity);
	}

	public void populateData(String ipv4, Long id) {
		Location location = locationGateway.getGeographicalLocation(ipv4);
		LocationRepositoryEntity locationRepositoryEntity = toLocationRepositoryEntity(location);
		locationRepositoryEntity.setId(id);

		Weather weather = weatherApplication.getWeatherFromLatLog(location.getLatitude(), location.getLongitude());
		weather.setId(null);

		locationRepositoryEntity.setWeather(toWeatherRepositoryEntity(weather));

		locationRepository.save(locationRepositoryEntity);
	}


	public Location getById(Long id) {
		Optional<LocationRepositoryEntity> locationEntity = locationRepository.findById(id);
        if (locationEntity.isPresent()) {
        	Location location = toLocation(locationEntity.get());
			if (locationEntity.get().getWeather() != null) {
			    location.setWeather(toWeather(locationEntity.get().getWeather()));
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

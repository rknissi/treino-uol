package uol.location.location.application;

import org.springframework.stereotype.Service;
import uol.location.location.gateway.LocationGateway;
import uol.location.location.domain.Location;
import uol.location.location.domain.Weather;
import uol.location.location.queue.LocationCreationMessage;
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

	public void populateData(LocationCreationMessage locationCreationMessage) {
		Location location = locationGateway.getGeographicalLocation(locationCreationMessage.getIp());
		LocationRepositoryEntity locationRepositoryEntity = toLocationRepositoryEntity(location);
		locationRepositoryEntity.setPersonId(locationCreationMessage.getId());

		Weather weather = weatherApplication.getWeatherFromLatLog(location.getLatitude(), location.getLongitude());
		weather.setId(null);

		locationRepositoryEntity.setWeather(toWeatherRepositoryEntity(weather));

		locationRepository.save(locationRepositoryEntity);
	}


	public Location getById(Long id) {
		Optional<LocationRepositoryEntity> locationEntity = locationRepository.findByPersonId(id);
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
		Optional<LocationRepositoryEntity> locationEntity = locationRepository.findByPersonId(id);
		if (locationEntity.isPresent()) {
		    locationRepository.deleteById(id);
			return true;
		}
		return false;
	}




}

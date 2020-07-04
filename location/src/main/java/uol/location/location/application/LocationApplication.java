package uol.location.location.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uol.location.location.objects.Location;
import uol.location.location.objects.LocationEntity;
import uol.location.location.objects.Weather;
import uol.location.location.objects.WeatherEntity;
import uol.location.location.repository.LocationJpaRepository;
import uol.location.location.repository.LocationRestRepository;

import java.util.Optional;

@Service
public class LocationApplication {

	private final LocationJpaRepository locationJpaRepository;
	private final LocationRestRepository locationRestRepository;
	private final WeatherApplication weatherApplication;

	public LocationApplication(LocationJpaRepository locationJpaRepository, LocationRestRepository locationRestRepository, WeatherApplication weatherApplication) {
		this.locationJpaRepository = locationJpaRepository;
		this.locationRestRepository = locationRestRepository;
		this.weatherApplication = weatherApplication;
	}

	public Location create(String ipv4) {
		Location location = locationRestRepository.getGeographicalLocation(ipv4);
		LocationEntity locationEntity = new LocationEntity();
		BeanUtils.copyProperties(location, locationEntity);

		Weather weather = weatherApplication.getWeatherFromLatLog(location.getLatitude(), location.getLongitude());
		weather.setId(null);
		WeatherEntity weatherEntity = new WeatherEntity();
		BeanUtils.copyProperties(weather, weatherEntity);
		locationEntity.setWeatherEntity(weatherEntity);

		location.setWeather(weather);

		locationJpaRepository.save(locationEntity);
		location.setId(locationEntity.getId());
		location.getWeather().setId(locationEntity.getWeatherEntity().getId());
		return location;
	}

	public Location getById(Long id) {
        Optional<LocationEntity> locationEntity = locationJpaRepository.findById(id);
        if (locationEntity.isPresent()) {
        	Location location = new Location();
			BeanUtils.copyProperties(locationEntity.get(), location);
			BeanUtils.copyProperties(locationEntity.get().getWeatherEntity(), location.getWeather());
			return location;
        }
        return null;
    }

	public boolean deleteById(Long id) {
		Optional<LocationEntity> locationEntity = locationJpaRepository.findById(id);
		if (locationEntity.isPresent()) {
		    locationJpaRepository.deleteById(id);
			return true;
		}
		return false;
	}




}

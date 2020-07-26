package uol.location.location.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uol.location.location.objects.Location;
import uol.location.location.repository.objects.LocationRepositoryEntity;
import uol.location.location.repository.LocationRepository;

import java.util.Optional;

@Service
public class LocationApplication {

	private final LocationRepository locationRepository;
	private final MessageSenderApplication messageSenderApplication;
	private final LocationAsyncApplication locationAsyncApplication;

	public LocationApplication(LocationRepository locationRepository, MessageSenderApplication messageSenderApplication, LocationAsyncApplication locationAsyncApplication) {
		this.locationRepository = locationRepository;
		this.messageSenderApplication = messageSenderApplication;
		this.locationAsyncApplication = locationAsyncApplication;
	}

	public Location create(String ipv4) {
	    Location location = new Location();
		LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
		locationRepository.save(locationRepositoryEntity);
		messageSenderApplication.sendMessage(ipv4, locationRepositoryEntity.getId());
		//locationAsyncApplication.populateData(ipv4, locationRepositoryEntity);
		BeanUtils.copyProperties(locationRepositoryEntity, location);
		return location;
	}


	public Location getById(Long id) {
        Optional<LocationRepositoryEntity> locationEntity = locationRepository.findById(id);
        if (locationEntity.isPresent()) {
        	Location location = new Location();
			BeanUtils.copyProperties(locationEntity.get(), location);
			BeanUtils.copyProperties(locationEntity.get().getWeather(), location.getWeather());
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

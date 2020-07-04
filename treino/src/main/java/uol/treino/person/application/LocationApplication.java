package uol.treino.person.application;

import org.springframework.stereotype.Component;
import uol.treino.person.repository.LocationRepository;
import uol.treino.person.objects.Location;

@Component
public class LocationApplication {

    private final LocationRepository locationRepository;

    public LocationApplication(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getById(Long id) {
        return locationRepository.getById(id);
    }

    public Location create(String ip) {
        return locationRepository.create(ip);
    }

    public void deleteByid(Long id) {
        locationRepository.delete(id);
    }
}

package uol.location.location.converter;

import uol.location.location.dto.Location;
import uol.location.location.endpoint.resource.LocationEndpointBody;
import uol.location.location.repository.entity.LocationRepositoryEntity;

public class LocationConverter {

    public static Location toLocation(LocationRepositoryEntity locationRepositoryEntity) {
        Location location = new Location();

        location.setId(locationRepositoryEntity.getId());
        location.setContinent(locationRepositoryEntity.getContinent());
        location.setCountry(locationRepositoryEntity.getCountry());
        location.setSubdivision1(locationRepositoryEntity.getSubdivision1());
        location.setSubdivision2(locationRepositoryEntity.getSubdivision2());
        location.setCity(locationRepositoryEntity.getCity());
        location.setLatitude(locationRepositoryEntity.getLatitude());
        location.setLongitude(locationRepositoryEntity.getLongitude());

        return location;
    }

    public static LocationRepositoryEntity toLocationRepositoryEntity(Location location) {
        LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();

        locationRepositoryEntity.setId(location.getId());
        locationRepositoryEntity.setContinent(location.getContinent());
        locationRepositoryEntity.setCountry(location.getCountry());
        locationRepositoryEntity.setSubdivision1(location.getSubdivision1());
        locationRepositoryEntity.setSubdivision2(location.getSubdivision2());
        locationRepositoryEntity.setCity(location.getCity());
        locationRepositoryEntity.setLatitude(location.getLatitude());
        locationRepositoryEntity.setLongitude(location.getLongitude());

        return locationRepositoryEntity;
    }

    public static LocationEndpointBody toLocationEndpointBody(Location location) {
        LocationEndpointBody locationEndpointBody = new LocationEndpointBody();

        locationEndpointBody.setId(location.getId());
        locationEndpointBody.setContinent(location.getContinent());
        locationEndpointBody.setCountry(location.getCountry());
        locationEndpointBody.setSubdivision1(location.getSubdivision1());
        locationEndpointBody.setSubdivision2(location.getSubdivision2());
        locationEndpointBody.setCity(location.getCity());
        locationEndpointBody.setLatitude(location.getLatitude());
        locationEndpointBody.setLongitude(location.getLongitude());

        return locationEndpointBody;
    }
}

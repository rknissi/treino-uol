package uol.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.location.location.repository.objects.LocationRepositoryEntity;

@Repository
public interface LocationRepository extends CrudRepository<LocationRepositoryEntity, Long> {
}

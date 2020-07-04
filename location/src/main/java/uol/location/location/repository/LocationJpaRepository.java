package uol.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.location.location.objects.LocationEntity;

@Repository
public interface LocationJpaRepository extends CrudRepository<LocationEntity, Long> {
}

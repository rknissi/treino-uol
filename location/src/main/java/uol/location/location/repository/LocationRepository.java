package uol.location.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.location.location.repository.entity.LocationRepositoryEntity;

import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<LocationRepositoryEntity, Long> {

    Optional<LocationRepositoryEntity> findByPersonId(Long personId);

}

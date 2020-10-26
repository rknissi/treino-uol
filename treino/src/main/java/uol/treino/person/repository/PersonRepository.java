package uol.treino.person.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.treino.person.repository.entity.PersonRepositoryEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonRepositoryEntity, Long> {
    @Query("from PersonRepositoryEntity p where p.trustyBirthDate is null")
    public Iterable<PersonRepositoryEntity> findNullTrustyBirthDate();


}

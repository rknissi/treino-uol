package uol.treino.person.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import uol.treino.person.objects.Location;
import uol.treino.person.objects.Person;
import uol.treino.person.repository.objects.PersonRepositoryEntity;
import uol.treino.person.repository.PersonRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonApplication {

    private final PersonRepository personRepository;
    private final LocationApplication locationApplication;

    public PersonApplication(PersonRepository personRepository, LocationApplication locationApplication) {
        this.personRepository = personRepository;
        this.locationApplication = locationApplication;
    }

    public Person create(Person person, String ip) {
        PersonRepositoryEntity personRepositoryEntity = new PersonRepositoryEntity();
        BeanUtils.copyProperties(person, personRepositoryEntity);
        Location location = locationApplication.create(ip);
        person.setLocation(location);
        personRepositoryEntity.setLocationId(location.getId());
        person.setId(personRepository.save(personRepositoryEntity).getId());
        return person;
    }

    public Person patch(Long id, Person person) {
        if (personRepository.existsById(id)) {
            PersonRepositoryEntity personRepositoryEntity = personRepository.findById(id).get();
            personRepository.save(applyDiff(personRepositoryEntity, person));
            BeanUtils.copyProperties(personRepositoryEntity, person);
            person.setLocation(locationApplication.getById(personRepositoryEntity.getLocationId()));
            return person;
        }
        return null;
    }

    public Person getById(Long id) {
        Optional<PersonRepositoryEntity> optionalPersonEntity = personRepository.findById(id);
        if (optionalPersonEntity.isPresent()) {
            PersonRepositoryEntity personRepositoryEntity = optionalPersonEntity.get();
            Person person = new Person();
            BeanUtils.copyProperties(personRepositoryEntity, person);
            person.setLocation(locationApplication.getById(personRepositoryEntity.getLocationId()));
            return person;
        }
        return null;
    }

    public List<Person> getAll() {
        Iterable<PersonRepositoryEntity> personEntities = personRepository.findAll();
        List<Person> persons = new LinkedList<>();
        personEntities.forEach(personRepositoryEntity -> {
            Person person = new Person();
            BeanUtils.copyProperties(personRepositoryEntity, person);
            person.setLocation(locationApplication.getById(personRepositoryEntity.getLocationId()));
            persons.add(person);
        });
        return persons;
    }

    public boolean delete(Long id) {
        Optional<PersonRepositoryEntity> personEntity = personRepository.findById(id);
        if (personEntity.isPresent()) {
            personRepository.deleteById(id);
            locationApplication.deleteByid(personEntity.get().getLocationId());
            return true;
        }
        return false;
    }

    private PersonRepositoryEntity applyDiff(PersonRepositoryEntity personRepositoryEntity, Person person) {
        if (!ObjectUtils.isEmpty(personRepositoryEntity) && !ObjectUtils.isEmpty(person)) {
            if (!ObjectUtils.isEmpty(person.getName())) {
                personRepositoryEntity.setName(person.getName());
            }
            if (!ObjectUtils.isEmpty(person.getAge())) {
                personRepositoryEntity.setAge(person.getAge());
            }
        }
        return personRepositoryEntity;
    }

}

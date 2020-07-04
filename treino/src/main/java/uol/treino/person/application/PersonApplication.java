package uol.treino.person.application;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import uol.treino.person.objects.Location;
import uol.treino.person.objects.Person;
import uol.treino.person.objects.PersonEntity;
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
        PersonEntity personEntity = new PersonEntity();
        BeanUtils.copyProperties(person, personEntity);
        Location location = locationApplication.create(ip);
        person.setLocation(location);
        personEntity.setLocationId(location.getId());
        person.setId(personRepository.save(personEntity).getId());
        return person;
    }

    public Person patch(Long id, Person person) {
        if (personRepository.existsById(id)) {
            PersonEntity personEntity = personRepository.findById(id).get();
            personRepository.save(applyDiff(personEntity, person));
            BeanUtils.copyProperties(personEntity, person);
            person.setLocation(locationApplication.getById(personEntity.getLocationId()));
            return person;
        }
        return null;
    }

    public Person getById(Long id) {
        Optional<PersonEntity> optionalPersonEntity = personRepository.findById(id);
        if (optionalPersonEntity.isPresent()) {
            PersonEntity personEntity = optionalPersonEntity.get();
            Person person = new Person();
            BeanUtils.copyProperties(personEntity, person);
            person.setLocation(locationApplication.getById(personEntity.getLocationId()));
            return person;
        }
        return null;
    }

    public List<Person> getAll() {
        Iterable<PersonEntity> personEntities = personRepository.findAll();
        List<Person> persons = new LinkedList<>();
        personEntities.forEach(personEntity -> {
            Person person = new Person();
            BeanUtils.copyProperties(personEntity, person);
            person.setLocation(locationApplication.getById(personEntity.getLocationId()));
            persons.add(person);
        });
        return persons;
    }

    public boolean delete(Long id) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);
        if (personEntity.isPresent()) {
            personRepository.deleteById(id);
            locationApplication.deleteByid(personEntity.get().getLocationId());
            return true;
        }
        return false;
    }

    private PersonEntity applyDiff(PersonEntity personEntity, Person person) {
        if (!ObjectUtils.isEmpty(personEntity) && !ObjectUtils.isEmpty(person)) {
            if (!ObjectUtils.isEmpty(person.getName())) {
                personEntity.setName(person.getName());
            }
            if (!ObjectUtils.isEmpty(person.getAge())) {
                personEntity.setAge(person.getAge());
            }
        }
        return personEntity;
    }

}

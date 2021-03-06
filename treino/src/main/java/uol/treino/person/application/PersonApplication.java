package uol.treino.person.application;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import uol.treino.person.domain.Person;
import uol.treino.person.queue.LocationCreationProducerApplication;
import uol.treino.person.queue.LocationDeleteProducerApplication;
import uol.treino.person.repository.entity.PersonRepositoryEntity;
import uol.treino.person.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static uol.treino.person.converter.PersonConverter.*;

@Component
public class PersonApplication {

    private final PersonRepository personRepository;
    private final LocationApplication locationApplication;
    private final LocationCreationProducerApplication locationCreationProducerApplication;
    private final LocationDeleteProducerApplication locationDeleteProducerApplication;


    public PersonApplication(PersonRepository personRepository, LocationApplication locationApplication, LocationCreationProducerApplication locationCreationProducerApplication, LocationDeleteProducerApplication locationDeleteProducerApplication) {
        this.personRepository = personRepository;
        this.locationApplication = locationApplication;
        this.locationCreationProducerApplication = locationCreationProducerApplication;
        this.locationDeleteProducerApplication = locationDeleteProducerApplication;
    }

    public Person create(Person person, String ip) {
        PersonRepositoryEntity personRepositoryEntity = toPersonRepositoryEntity(person);
        person.setId(personRepository.save(personRepositoryEntity).getId());
        person.setAge(calculateAge(personRepositoryEntity.getBirthDate()));
        person.setBirthDate(personRepositoryEntity.getBirthDate());

        locationCreationProducerApplication.sendMessage(ip, person.getId());

        return person;
    }

    public Person patch(Long id, Person person) {
        if (personRepository.existsById(id)) {
            PersonRepositoryEntity personRepositoryEntity = personRepository.findById(id).get();
            if  (personRepositoryEntity.isValid()) {
                personRepository.save(applyDiff(personRepositoryEntity, person));
                person = toPerson(personRepositoryEntity);
                person.setAge(calculateAge(personRepositoryEntity.getBirthDate()));
                person.setLocation(locationApplication.getById(personRepositoryEntity.getId()));
                return person;
            }
        }
        return null;
    }

    public Person getById(Long id) {
        Optional<PersonRepositoryEntity> optionalPersonEntity = personRepository.findById(id);
        if (optionalPersonEntity.isPresent() && optionalPersonEntity.get().isValid()) {
            PersonRepositoryEntity personRepositoryEntity = optionalPersonEntity.get();
            Person person = toPerson(personRepositoryEntity);
            person.setAge(calculateAge(personRepositoryEntity.getBirthDate()));
            person.setLocation(locationApplication.getById(personRepositoryEntity.getId()));
            return person;
        }
        return null;
    }

    public List<Person> getAll() {
        Iterable<PersonRepositoryEntity> personEntities = personRepository.findAll();
        List<Person> persons = new LinkedList<>();
        personEntities.forEach(personRepositoryEntity -> {
            if (personRepositoryEntity.isValid()) {
                Person person = toPerson(personRepositoryEntity);
                person.setAge(calculateAge(personRepositoryEntity.getBirthDate()));
                person.setLocation(locationApplication.getById(personRepositoryEntity.getId()));
                persons.add(person);
            }
        });
        return persons;
    }

    public boolean delete(Long id) {
        Optional<PersonRepositoryEntity> personEntity = personRepository.findById(id);
        if (personEntity.isPresent() && personEntity.get().isValid()) {
            personEntity.get().setValid(false);
            personRepository.save(personEntity.get());
            locationDeleteProducerApplication.sendMessage(personEntity.get().getId());
            return true;
        }
        return false;
    }

    private PersonRepositoryEntity applyDiff(PersonRepositoryEntity personRepositoryEntity, Person person) {
        if (!ObjectUtils.isEmpty(personRepositoryEntity) && !ObjectUtils.isEmpty(person)) {
            if (!ObjectUtils.isEmpty(person.getName())) {
                personRepositoryEntity.setName(person.getName());
            }
            if (personRepositoryEntity.isTrustyBirthDate()) {
                if (!ObjectUtils.isEmpty(person.getBirthDate())) {
                    personRepositoryEntity.setBirthDate(person.getBirthDate());
                }
            } else {
                if (!ObjectUtils.isEmpty(person.getAge()))
                personRepositoryEntity.setBirthDate(LocalDate.now().minusYears(person.getAge()));
            }
        }
        return personRepositoryEntity;
    }

    private Integer calculateAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updatePersonTable() {
        Iterable<PersonRepositoryEntity> updatableUsers = personRepository.findNullTrustyBirthDate();
        updatableUsers.forEach(user -> {
            user.setBirthDate(LocalDate.now().minusYears(user.getAge()));
            user.setTrustyBirthDate(false);
            personRepository.save(user);
        });
    }

}

package uol.treino.person.converter;

import uol.treino.person.domain.Person;
import uol.treino.person.repository.entity.PersonRepositoryEntity;

import java.time.LocalDate;
import java.time.Period;

public class PersonConverter {

    public static PersonRepositoryEntity toPersonRepositoryEntity(Person person) {
        PersonRepositoryEntity personRepositoryEntity = new PersonRepositoryEntity();

        personRepositoryEntity.setId(person.getId());
        personRepositoryEntity.setName(person.getName());
        if (person.getBirthDate() != null) {
            personRepositoryEntity.setBirthDate(person.getBirthDate());
            personRepositoryEntity.setAge(Period.between(person.getBirthDate(), LocalDate.now()).getYears());
            personRepositoryEntity.setTrustyBirthDate(true);
        } else {
            personRepositoryEntity.setBirthDate(LocalDate.now().minusYears(person.getAge()));
            personRepositoryEntity.setAge(person.getAge());
            personRepositoryEntity.setTrustyBirthDate(false);
        }

        return personRepositoryEntity;
    }
    public static Person toPerson(PersonRepositoryEntity personRepositoryEntity) {
        Person person = new Person();

        person.setId(personRepositoryEntity.getId());
        person.setName(personRepositoryEntity.getName());
        person.setAge(personRepositoryEntity.getAge());
        person.setBirthDate(personRepositoryEntity.getBirthDate());

        return person;
    }
}

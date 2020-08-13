package uol.treino.person.converter;

import uol.treino.person.dto.Person;
import uol.treino.person.repository.entity.PersonRepositoryEntity;

public class PersonConverter {

    public static PersonRepositoryEntity toPersonRepositoryEntity(Person person) {
        PersonRepositoryEntity personRepositoryEntity = new PersonRepositoryEntity();

        personRepositoryEntity.setId(person.getId());
        personRepositoryEntity.setName(person.getName());
        personRepositoryEntity.setAge(person.getAge());

        return personRepositoryEntity;
    }
    public static Person toPerson(PersonRepositoryEntity personRepositoryEntity) {
        Person person = new Person();

        person.setId(personRepositoryEntity.getId());
        person.setName(personRepositoryEntity.getName());
        person.setAge(personRepositoryEntity.getAge());

        return person;
    }
}

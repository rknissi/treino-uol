package uol.treino;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uol.treino.person.application.PersonApplication;
import uol.treino.person.objects.Person;
import uol.treino.person.repository.PersonRepository;
import uol.treino.person.repository.objects.PersonRepositoryEntity;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class PersonCT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    PersonApplication personApplication;

    @Autowired
    PersonRepository personRepository;

    @Before
    public void populateDatabase() {
        createPerson(1L, "Teste", 15, 3L);
    }

    @Test
    public void createPersonCT() {

        String name = "asd";
        Integer age = 123;

        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        Person personResponse = personApplication.create(person, "127.0.0.1");

        Assert.assertEquals("2", personResponse.getId().toString());
    }

    @Test
    public void getPersonByIdAndExistsCT() {
        Person personResponse = personApplication.getById(1L);

        Assert.assertEquals("1", personResponse.getId().toString());
        Assert.assertEquals("Teste", personResponse.getName());
        Assert.assertEquals("15", personResponse.getAge().toString());
        Assert.assertEquals("1", personResponse.getLocation().getId().toString());
        Assert.assertEquals("Brazil", personResponse.getLocation().getCountry());
        Assert.assertEquals("São Paulo", personResponse.getLocation().getCity());
        Assert.assertEquals("1", personResponse.getLocation().getWeather().getId().toString());
        Assert.assertEquals("13", personResponse.getLocation().getWeather().getMinTemp().toString());
        Assert.assertEquals("15", personResponse.getLocation().getWeather().getMaxTemp().toString());
    }

    @Test
    public void patchPersonCT() {
        createPerson(2L, "Teste", 15, 3L);

        String name = "Novo Teste";
        Integer age = 123;

        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        Person personResponse = personApplication.patch(2L, person);

        Assert.assertEquals("2", personResponse.getId().toString());
        Assert.assertEquals("Novo Teste", personResponse.getName());
        Assert.assertEquals("123", personResponse.getAge().toString());
    }

    @Test
    public void deletePersonCT() {
        createPerson(2L, "Teste", 15, 3L);

        Boolean result = personApplication.delete(2L);

        Assert.assertEquals(true, result);

        Person personResponse = personApplication.getById(2L);

        Assert.assertNull(personResponse);
    }

    private void createPerson (Long id, String name, Integer age, Long locationId) {
        PersonRepositoryEntity person = new PersonRepositoryEntity();

        person.setId(id);
        person.setName(name);
        person.setAge(age);
        person.setLocationId(locationId);

        personRepository.save(person);

    }

}

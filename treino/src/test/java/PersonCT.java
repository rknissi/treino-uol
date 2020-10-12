import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uol.treino.TreinoUolApplication;
import uol.treino.person.application.PersonApplication;
import uol.treino.person.domain.Person;
import uol.treino.person.repository.PersonRepository;
import uol.treino.person.repository.entity.PersonRepositoryEntity;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = TreinoUolApplication.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonCT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    PersonApplication personApplication;

    @Autowired
    PersonRepository personRepository;

    @Before
    public void populateDatabase() throws Exception {
        Broker broker = new Broker();
        BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", "6000");
        brokerOptions.setConfigProperty("qpid.broker.defaultPreferenceStoreAttributes", "{\"type\": \"Noop\"}");
        brokerOptions.setConfigProperty("qpid.vhost", "localhost");
        brokerOptions.setConfigurationStoreType("Memory");
        brokerOptions.setStartupLoggedToSystemOut(false);
        broker.startup(brokerOptions);

        createPerson(1L, "Teste", 15);
        LocalDate now = LocalDate.now();
        createPerson(2L, "Teste 2", LocalDate.of(now.getYear() - 22, now.getMonth(), now.getDayOfMonth()));
    }

    @Test
    public void createPersonCT() {

        List<Person> personList = personApplication.getAll();

        String name = "asd";
        Integer age = 123;

        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        Person personResponse = personApplication.create(person, "127.0.0.1");

        Assert.assertThat(personResponse.getId(), is(notNullValue()));
        Assert.assertEquals("asd", personResponse.getName());
        Assert.assertEquals("123", personResponse.getAge().toString());
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
    public void getPersonByIdAndExistsWithCorrectAgeCT() {
        Person personResponse = personApplication.getById(2L);

        Assert.assertEquals("2", personResponse.getId().toString());
        Assert.assertEquals("Teste 2", personResponse.getName());
        Assert.assertEquals("22", personResponse.getAge().toString());
        Assert.assertEquals("2", personResponse.getLocation().getId().toString());
        Assert.assertEquals("Brazil", personResponse.getLocation().getCountry());
        Assert.assertEquals("São Paulo", personResponse.getLocation().getCity());
        Assert.assertEquals("2", personResponse.getLocation().getWeather().getId().toString());
        Assert.assertEquals("13", personResponse.getLocation().getWeather().getMinTemp().toString());
        Assert.assertEquals("15", personResponse.getLocation().getWeather().getMaxTemp().toString());
    }

    @Test
    public void patchPersonCT() {
        createPerson(3L, "Teste", 15);

        String name = "Novo Teste";
        Integer age = 123;

        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        Person personResponse = personApplication.patch(3L, person);

        Assert.assertEquals("3", personResponse.getId().toString());
        Assert.assertEquals("Novo Teste", personResponse.getName());
        Assert.assertEquals("123", personResponse.getAge().toString());
    }

    @Test
    public void deletePersonCT() {
        createPerson(3L, "Teste", 15);

        Boolean result = personApplication.delete(3L);

        Assert.assertEquals(true, result);

        Person personResponse = personApplication.getById(3L);

        Assert.assertNull(personResponse);
    }

    private void createPerson (Long id, String name, Integer age) {
        PersonRepositoryEntity person = new PersonRepositoryEntity();

        person.setId(id);
        person.setName(name);
        person.setAge(age);
        person.setCreationDate(LocalDate.now());

        personRepository.save(person);
    }

    private void createPerson (Long id, String name, LocalDate birthDate) {
        PersonRepositoryEntity person = new PersonRepositoryEntity();

        person.setId(id);
        person.setName(name);
        person.setBirthDate(birthDate);
        person.setCreationDate(LocalDate.now());

        personRepository.save(person);
    }

}

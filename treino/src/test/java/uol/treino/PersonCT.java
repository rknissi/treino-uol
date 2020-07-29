package uol.treino;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uol.treino.person.application.PersonApplication;
import uol.treino.person.objects.Person;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonCT {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Autowired
    PersonApplication personApplication;


    @Test
    public void createPersonCT() {

        String name = "asd";
        Integer age = 123;

        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        Person personResponse = personApplication.create(person, "127.0.0.1");

        Assert.assertEquals("1", personResponse.getId().toString());
    }

    @Test
    public void getPersonByIdAndExistsCT() {

        Person personResponse = personApplication.getById(1L);

        Assert.assertEquals("1", personResponse.getId().toString());
        Assert.assertEquals("Teste", personResponse.getName());
        Assert.assertEquals("15", personResponse.getAge());
        Assert.assertEquals("3", personResponse.getLocation().getId().toString());
        Assert.assertEquals("Brazil", personResponse.getLocation().getCountry());
        Assert.assertEquals("São Paulo", personResponse.getLocation().getCity());
        Assert.assertEquals("3", personResponse.getLocation().getWeather().getId().toString());
        Assert.assertEquals("14", personResponse.getLocation().getWeather().getMinTemp().toString());
        Assert.assertEquals("16", personResponse.getLocation().getWeather().getMaxTemp().toString());
    }

}

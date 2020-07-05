package uol.treino;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import uol.treino.person.objects.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void completeIT() throws Exception {

        Person person = new Person();
        person.setName("Teste");
        person.setAge(17);

        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons"), HttpMethod.POST, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        assertEquals(jsonObject.get("name"), "Teste");
        assertEquals(jsonObject.get("age"), 17);

        getPerson();

    }

    public void getPerson() throws Exception {
        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/1"), HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(jsonObject.get("name"), "Teste");
        assertEquals(jsonObject.get("age"), 18);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

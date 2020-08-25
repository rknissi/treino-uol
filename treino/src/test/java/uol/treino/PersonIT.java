package uol.treino;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uol.treino.person.domain.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void returnCreatedIfCreatedPersonSuccessfullyIT() throws Exception {
        ResponseEntity<String> response = createPerson("Teste", 17);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        assertEquals(jsonObject.get("name"), "Teste");
        assertEquals(jsonObject.get("age"), 17);
    }

    @Test
    public void returnOkWhenSuccessfullyFindingUserByIdIT() throws Exception {
        createPerson("Teste", 18);
        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/1"), HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(jsonObject.get("name"), "Teste");
        assertEquals(jsonObject.get("age"), 18);
    }

    @Test
    public void returnOkWhenDeletedPersonSuccessfullyIT() throws Exception {
        createPerson("TesteDeleted", 21);
        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/1"), HttpMethod.DELETE, entity, String.class);

        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void returnOkWhenPatchNewDataIT() throws Exception {
        createPerson("Teste", 18);

        Person person = new Person();
        person.setName("TestePatch");
        person.setAge(17);

        HttpEntity<Person> entity = new HttpEntity<>(person , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/1"), HttpMethod.PATCH, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(jsonObject.get("name"), "TestePatch");
        assertEquals(jsonObject.get("age"), 17);
    }

    @Test
    public void returnNotFoundWhenUserDoesNotExistIT() throws Exception {
        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/1"), HttpMethod.GET, entity, String.class);

        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    private ResponseEntity createPerson(String name, Integer age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);

        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        return restTemplate.exchange(
                createURLWithPort("/persons"), HttpMethod.POST, entity, String.class);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

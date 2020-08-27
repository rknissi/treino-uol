package uol.treino;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonIT {

    @LocalServerPort
    private int port = 8080;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    ObjectMapper objectMapper = new ObjectMapper();

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
        ResponseEntity<String> response =  createPerson("Teste", 18);

        Person person = objectMapper.readValue(response.getBody(), Person.class);

        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> getResponse = restTemplate.exchange(
                createURLWithPort("/persons/" + person.getId()), HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(getResponse.getBody());

        assertTrue(getResponse.getStatusCode() == HttpStatus.OK);
        assertEquals(jsonObject.get("name"), "Teste");
        assertEquals(jsonObject.get("age"), 18);
    }

    @Test
    public void returnOkWhenDeletedPersonSuccessfullyIT() throws Exception {
        ResponseEntity<String> response =  createPerson("TesteDeleted", 21);
        Person person = objectMapper.readValue(response.getBody(), Person.class);

        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                createURLWithPort("/persons/" + person.getId()), HttpMethod.DELETE, entity, String.class);

        assertTrue(deleteResponse.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void returnOkWhenPatchNewDataIT() throws Exception {
        ResponseEntity<String> response = createPerson("Teste", 18);
        Person person = objectMapper.readValue(response.getBody(), Person.class);

        Person newPerson = new Person();
        newPerson.setName("TestePatch");
        newPerson.setAge(17);

        HttpEntity<Person> entity = new HttpEntity<>(newPerson , headers);

        ResponseEntity<String> patchResponse = restTemplate.exchange(
                createURLWithPort("/persons/" + person.getId()), HttpMethod.PATCH, entity, String.class);

        JSONObject jsonObject = new JSONObject(patchResponse.getBody());

        assertTrue(patchResponse.getStatusCode() == HttpStatus.OK);
        assertEquals(jsonObject.get("name"), "TestePatch");
        assertEquals(jsonObject.get("age"), 17);
    }

    @Test
    public void returnNotFoundWhenUserDoesNotExistIT() throws Exception {
        HttpEntity<Person> entity = new HttpEntity<>(null , headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/persons/0"), HttpMethod.GET, entity, String.class);

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

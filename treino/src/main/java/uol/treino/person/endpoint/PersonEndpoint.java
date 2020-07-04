package uol.treino.person.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import uol.treino.person.objects.Person;
import uol.treino.person.application.PersonApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PersonEndpoint {

    private final PersonApplication personApplication;

    public PersonEndpoint(PersonApplication personApplication) {
        this.personApplication = personApplication;
    }

    @GetMapping("/persons")
    List<Person> getAll() {
        return personApplication.getAll();
    }

    @GetMapping("/persons/{id}")
    ResponseEntity getById(@PathVariable(value="id") Long id) {
        Person person =  personApplication.getById(id);
        if (!ObjectUtils.isEmpty(person)) {
            return ResponseEntity.status(HttpStatus.OK).body(person);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/persons")
    ResponseEntity create(@RequestBody Person person, HttpServletRequest servletRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personApplication.create(person, servletRequest.getRemoteAddr()));
    }

    @PatchMapping("/persons/{id}")
    ResponseEntity patch(@PathVariable(value="id") Long id, @RequestBody Person person) {
        Person changedPerson =  personApplication.patch(id, person);
        if (changedPerson != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(changedPerson);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/persons/{id}")
    ResponseEntity delete(@PathVariable(value="id") Long id) {
        boolean deleted = personApplication.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

package uol.treino.person.repository.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PersonRepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer age;
    private LocalDate birthDate;
    private boolean trustyBirthDate;

    private boolean valid;

    public PersonRepositoryEntity() {
        this.valid = true;
    }

    public PersonRepositoryEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isTrustyBirthDate() {
        return trustyBirthDate;
    }

    public void setTrustyBirthDate(boolean trustyBirthDate) {
        this.trustyBirthDate = trustyBirthDate;
    }
}

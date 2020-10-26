package uol.treino.person.repository.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person")
public class PersonRepositoryEntity {

    @Id
    @NotNull
    @SequenceGenerator(name="person_generator",sequenceName="person_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="person_generator")
    private Long id;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @NotNull
    @Column(name="birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name="trusty_birth_date")
    private Boolean trustyBirthDate;

    @NotNull
    @Column(name="valid")
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

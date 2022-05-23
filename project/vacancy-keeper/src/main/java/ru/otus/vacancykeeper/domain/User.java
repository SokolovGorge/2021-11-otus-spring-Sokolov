package ru.otus.vacancykeeper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "vacancy", name = "cl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "passwrd", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "sur_name", nullable = false)
    private String surname;

    @Column(name = "path_name")
    private String pathname;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;
}

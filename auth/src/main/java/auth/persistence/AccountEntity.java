package auth.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    public Long id;

    @Column(name = "login", nullable = false, unique = true)
    public String login;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "roles", nullable = false)
    public String roles;
}
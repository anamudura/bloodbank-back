package com.example.bloodbank.appuser;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

//PANA DATA VIITOARE - CE FEATURE VREM IN PLUS
//O PARTE DIN DOCUMENTATIE
//DIAGRGRAMA USE CASE
//DIAGRAMA DE SECVENTA
// 18 - IMPL
// 25 - PROICETELELE

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nume;

    private String email;

    private String password;

    private String location;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "locations_table",
            joinColumns =
                    { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "location_id", referencedColumnName = "id") })
    @JsonManagedReference
    private Locations bloodbank;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Appointment> app;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Users(String nume, String email, String password, String locatie, Collection<Role> roles) {
        this.nume = nume;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.location = locatie;
    }
}
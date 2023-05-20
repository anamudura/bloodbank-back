package com.example.bloodbank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
//ADAUGAT ULTERIOR - FOLOSESTE SECURITATE
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String email;
    private String password;
    private String name;
    private String specialisation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    public Doctor(String email, String password, String name, String specialisation, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.specialisation = specialisation;
        this.roles = roles;
    }
    public Collection<Role> getRoles() {
        return roles;
    }
}

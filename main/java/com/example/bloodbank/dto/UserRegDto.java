package com.example.bloodbank.dto;

import com.example.bloodbank.appuser.Role;
import com.example.bloodbank.appuser.Users;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String location;
    private Collection<Role> roles;


    public UserRegDto(String name, String email, String password, String locatie, Long id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = locatie;
    }

    public UserRegDto(Users user) {
        this.name = user.getNume();
        this.email = user.getEmail();
        this.password = user.getPassword();;
        this.location = user.getLocation();
        this.roles = user.getRoles();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


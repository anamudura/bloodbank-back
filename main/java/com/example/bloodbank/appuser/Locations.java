package com.example.bloodbank.appuser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int inceput;
    private int sfarsit;

    private int nrmaximdon;

    @OneToMany(mappedBy = "locations")
    @JsonManagedReference
    private List<Appointment> app;

    @OneToOne(mappedBy = "bloodbank")
    @JsonBackReference
    private Users user;

}

package com.example.bloodbank.appuser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodtype;
    private LocalDate prog;

    //DE ADAUGAT SI IN FRONT END CONFIRMARI PROGRAMARI PENTRU STATISTICI
    private Boolean confirmed;
    @ManyToOne
    @JsonBackReference
    private Locations locations;

    @ManyToOne
    @JsonBackReference
    private Users user;

    public Appointment(String bloodtype, LocalDate prog) {
        this.bloodtype = bloodtype;
        this.prog = prog;
        this.confirmed = false;
    }
}

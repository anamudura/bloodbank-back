package com.example.bloodbank.appuser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodtype;
    private LocalDate prog;

    @ManyToOne
    @JsonBackReference
    private Locations locations;

    public Appointment(String bloodtype, LocalDate prog) {
        this.bloodtype = bloodtype;
        this.prog = prog;
    }
}

package com.example.bloodbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankStatistics {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private float numberOfLiters;
    private int numberOfTotalAppointments;
    private int numberOfConfirmedAppointments;
    private int numberofDeniedApp;
    private int O;
    private int A;
    private int B;
    private int AB;


}

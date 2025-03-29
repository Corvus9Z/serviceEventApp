package com.ServiceAggregatingEvents.project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String lastName;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password",unique = true)
    private String password;

}

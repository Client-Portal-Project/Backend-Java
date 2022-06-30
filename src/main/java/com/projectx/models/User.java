package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    Will handle the User's information and functionality
    (Owner, Applicant, Client)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;
    @Column(name="approved", nullable=false)
    private boolean approved;
    @Column(name="email", unique = true, nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="first_name",nullable = false)
    private String firstName;
    @Column(name="last_name", nullable = false)
    private String lastName;
}
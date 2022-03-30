package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    Will handle the User's information and functionality from Auth0
    (Owner, Applicant, Client)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer userId;

    @Column(nullable = true)
    private String birthdate;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private Boolean email_verified;

    @Column(nullable = true)
    private String given_name;

    @Column(nullable = true)
    private String family_name;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String phone_number;

    @Column(nullable = true)
    private Boolean phone_number_verified;

    @Column(nullable = true)
    private String picture;
}

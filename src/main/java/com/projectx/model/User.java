package com.projectx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/*
    Will handle the User's information and functionality
    (Owner, Applicant, Client)
 */


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer userId;
    @Column(name = "user_email",unique = true, nullable = false)
    private String email;
    @Column(name = "user_password",nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;



    @OneToOne(mappedBy = "userId")
    @JsonIgnore
    private Applicant applicant;

    @OneToOne(mappedBy = "userId")
    @JsonIgnore
    private OwnerUser ownerUser;

    @OneToOne(mappedBy = "userId")
    @JsonIgnore
    private ClientUser clientUser;

}

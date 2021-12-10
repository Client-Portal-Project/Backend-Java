package com.projectx.clientportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Will handle the Applicant's information and functionality

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Integer applicantId;
    @Column
    private Byte[] resume;
    @Column
    private String aboutMe;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String educationLevel;
    @Column
    private String educationField;
    @Column
    private String EmploymentStatus;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
}

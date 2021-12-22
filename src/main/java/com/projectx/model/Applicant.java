package com.projectx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String aboutMe;
    @Column
    private String educationLevel;
    @Column
    private String educationField;
    @Column
    private String employmentStatus;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "ApplicantSkills",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    Set<Skill> applicantSkills;

    public Applicant(int applicant_id, String aboutMe, String educationLevel, String educationField, String EmploymentStatus, User user) {
        this.applicantId = applicant_id;
        this.aboutMe = aboutMe;
        this.educationLevel = educationLevel;
        this.educationField = educationField;
        this.employmentStatus = EmploymentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }

    public Applicant(String aboutMe, String educationLevel, String educationField, String EmploymentStatus, User user) {
        this.aboutMe = aboutMe;
        this.educationLevel = educationLevel;
        this.educationField = educationField;
        this.employmentStatus = EmploymentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }
}

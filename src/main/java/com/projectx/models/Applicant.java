package com.projectx.models;

import lombok.*;

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
    @Column
    private Integer applicantId;
    @Column
    private String aboutMe;
    @Column
    private String educationLevel;
    @Column
    private String educationField;
    @Column
    private String employmentStatus;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "ApplicantSkills",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    Set<Skill> applicantSkills;

    public Applicant(int applicantId, String aboutMe, String educationLevel, String educationField,
                     String employmentStatus, User user) {
        this.applicantId = applicantId;
        this.aboutMe = aboutMe;
        this.educationLevel = educationLevel;
        this.educationField = educationField;
        this.employmentStatus = employmentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }

    public Applicant(String aboutMe, String educationLevel, String educationField, String employmentStatus,
                     User user) {
        this.aboutMe = aboutMe;
        this.educationLevel = educationLevel;
        this.educationField = educationField;
        this.employmentStatus = employmentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }

	public int getApplicantId() {
		// TODO Auto-generated method stub
		return this.applicantId;
	}
}

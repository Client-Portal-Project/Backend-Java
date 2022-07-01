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
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicant_id;
    @Column
    private String about_me;
    @Column
    private String education_level;
    @Column
    private String education_field;
    @Column
    private String employment_status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "applicantskills",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    Set<Skill> applicantSkills;

    public Applicant(int applicantId, String aboutMe, String educationLevel, String educationField,
                     String employmentStatus, User user) {
        this.applicant_id = applicantId;
        this.about_me = aboutMe;
        this.education_level = educationLevel;
        this.education_field = educationField;
        this.employment_status = employmentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }

    public Applicant(String aboutMe, String educationLevel, String educationField, String employmentStatus,
                     User user) {
        this.about_me = aboutMe;
        this.education_level = educationLevel;
        this.education_field = educationField;
        this.employment_status = employmentStatus;
        this.user = user;
        this.applicantSkills = new HashSet<>();
    }

	public int getApplicantId() {
		// TODO Auto-generated method stub
		return this.applicant_id;
	}
}

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
	@Column(name = "applicant_id")
	private int applicantId;
	@Column(name = "about_me")
	private String aboutMe;
	@Column(name = "education_field")
	private String educationField;
	@Column(name = "education_level")
	private String educationLevel;
	@Column(name = "employment_status")
	private String employmentStatus;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk")
	private User user;
	@JoinColumn(name = "skill_id")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Skill> skill;

	public Applicant(int applicantId, String aboutMe, String educationLevel, String educationField,
			String employmentStatus, User user) {
		this.applicantId = applicantId;
		this.aboutMe = aboutMe;
		this.educationLevel = educationLevel;
		this.educationField = educationField;
		this.employmentStatus = employmentStatus;
		this.user = user;
		this.skill = new HashSet<>();
	}

	public Applicant(String aboutMe, String educationLevel, String educationField, String employmentStatus, User user) {
		this.aboutMe = aboutMe;
		this.educationLevel = educationLevel;
		this.educationField = educationField;
		this.employmentStatus = employmentStatus;
		this.user = user;
		this.skill = new HashSet<>();
	}

	public int getApplicantId() {
		// TODO Auto-generated method stub
		return this.applicantId;
	}
}

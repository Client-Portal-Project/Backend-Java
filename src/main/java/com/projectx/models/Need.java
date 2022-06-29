package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Need class describes the needs for an application given by a specific client
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "need")
public class Need {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "need_id")
	private int needId;
	@Column(name = "amount_needed")
	private int amountNeeded;
	@Column(name = "amount_fulfilled")
	private int amountFulfilled;
	@Column(name = "education_field")
	private String educationField; // i.e. Biology, Computer Science
	@Column(name = "years_experience")
	private int yearsExperience;
	@Column(name = "extra_description")
	private String extraDescription;
	@Column(name = "job_title")
	private String jobTitle;
	@Column(name = "education_level")
	private String educationLevel; // i.e. Associate's Bachelor's...
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "skills_id")
	Set<Skill> skills;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "applicants_id")
	Set<Applicant> applicants;

	public Need(int amountNeeded, int amountFulfilled, String educationField, int yearsExperience,
			String extraExperience, String jobTitle, Client client) {
		this.amountNeeded = amountNeeded;
		this.amountFulfilled = amountFulfilled;
		this.educationField = educationField;
		this.yearsExperience = yearsExperience;
		this.extraDescription = extraExperience;
		this.jobTitle = jobTitle;
		this.client = client;
		this.skills = new HashSet<>();
	}

	public Need(int need_id, int amountNeeded, int amountFulfilled, String educationField, int yearsExperience,
			String extraExperience, String jobTitle, Client client) {
		this.needId = need_id;
		this.amountNeeded = amountNeeded;
		this.amountFulfilled = amountFulfilled;
		this.educationField = educationField;
		this.yearsExperience = yearsExperience;
		this.extraDescription = extraExperience;
		this.jobTitle = jobTitle;
		this.client = client;
		this.skills = new HashSet<>();
	}

}

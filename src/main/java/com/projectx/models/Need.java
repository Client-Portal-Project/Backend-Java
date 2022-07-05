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
	@Column(name = "amount_fulfilled")
	private int amountFulfilled;
	@Column(name = "amount_needed")
	private int amountNeeded;
	@Column(name = "education_field")
	private String educationField; // i.e. Biology, Computer Science
	@Column(name = "education_level")
	private String educationLevel; // i.e. Associate's Bachelor's...
	@Column(name = "extra_description")
	private String extraDescription;
	@Column(name = "job_title")
	private String jobTitle;
	@Column(name = "years_experience")
	private int yearsExperience;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "skill_id")
	Set<Skill> skill;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "applicant_id")
	Set<Applicant> applicant;

	public Need(int amountNeeded, int amountFulfilled, String educationField, int yearsExperience,
			String extraExperience, String jobTitle, Client client) {
		this.amountNeeded = amountNeeded;
		this.amountFulfilled = amountFulfilled;
		this.educationField = educationField;
		this.yearsExperience = yearsExperience;
		this.extraDescription = extraExperience;
		this.jobTitle = jobTitle;
		this.client = client;
		this.skill = new HashSet<>();
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
		this.skill = new HashSet<>();
	}

}

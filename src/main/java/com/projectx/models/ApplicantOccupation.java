package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    Will handle information and functionality for the
    applicant's work history and their marketability
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applicant_occupation")
public class ApplicantOccupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="applicant_occupationl_id")
    private int applicantOccupationalId;
    @Column(name="job_title")
    private String jobTitle;
    @Column(name="year_experience")
    private int yearsExperience;
    @Column(name="open_market", nullable=false)
    private boolean openMarket;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;
 
    public ApplicantOccupation(String jobTitle, int yeasExperience, Boolean openMarket, Applicant applicant) {
        this.jobTitle = jobTitle;
        this.yearsExperience = yeasExperience;
        this.openMarket = openMarket;
        this.applicant = applicant;
    }

    public ApplicantOccupation(int applicantOccupationalId, String jobTitle, int yeasExperience, Boolean openMarket, Applicant applicant) {
        this.applicantOccupationalId = applicantOccupationalId;
        this.jobTitle = jobTitle;
        this.yearsExperience = yeasExperience;
        this.openMarket = openMarket;
        this.applicant = applicant;
    }
}

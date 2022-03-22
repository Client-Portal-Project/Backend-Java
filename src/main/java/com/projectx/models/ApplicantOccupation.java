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
@Table(name = "ApplicantOccupations")
public class ApplicantOccupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicantOccupationalId;
    @Column
    private String jobTitle;
    @Column
    private Integer yearsExperience;
    @Column
    private Boolean openMarket;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Applicant applicant;

    public ApplicantOccupation(String jobTitle, int yeasExperience, Boolean openMarket, Applicant applicant) {
        this.jobTitle = jobTitle;
        this.yearsExperience = yeasExperience;
        this.openMarket = openMarket;
        this.applicant = applicant;
    }

    public ApplicantOccupation(Integer applicantOccupationalId) {
        this.applicantOccupationalId = applicantOccupationalId;
    }
}

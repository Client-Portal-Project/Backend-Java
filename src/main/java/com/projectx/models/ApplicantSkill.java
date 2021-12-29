package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Join table to display which skills an applicant claims to possess

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ApplicantSkills")
public class ApplicantSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicantSkillId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Applicant applicant;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Skill skill;
}

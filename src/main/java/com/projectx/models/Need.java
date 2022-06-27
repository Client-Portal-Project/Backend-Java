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
@Table(name = "needs")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer need_id;
    @Column
    @Nullable
    private Integer amount_needed;
    @Column
    @Nullable
    private Integer amount_fulfilled;
    @Column
    @Nullable
    private String education_field; // i.e. Biology, Computer Science
    @Column
    @Nullable
    private Integer years_experience;
    @Column
    @Nullable
    private String extra_description;
    @Column
    @Nullable
    private String job_title;
    @Column
    @Nullable
    private String education_level; // i.e. Associate's Bachelor's...
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Client client;
    @ManyToMany
    @Nullable
    @JoinTable(
            name = "need_skill",
            joinColumns = @JoinColumn(name = "need_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    Set<Skill> skills;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
            @Nullable
    Set<Applicant> chosenApplicants;

    public Need (Integer amountNeeded, Integer amountFulfilled, String educationField, Integer yearsExperience,
                 String extraExperience, String jobTitle, Client client) {
        this.amount_needed = amountNeeded;
        this.amount_fulfilled = amountFulfilled;
        this.education_field = educationField;
        this.years_experience = yearsExperience;
        this.extra_description = extraExperience;
        this.job_title = jobTitle;
        this.client = client;
        this.skills = new HashSet<>();
    }

}

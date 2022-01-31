package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "Needs")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer needId;
    @Column
    private Integer amountNeeded;
    @Column
    private Integer amountFulfilled;
    @Column
    private String educationField; // i.e. Biology, Computer Science
    @Column
    private Integer yearsExperience;
    @Column
    private String extraDescription;
    @Column
    private String jobTitle;
    @Column
    private String educationLevel; // i.e. Associate's Bachelor's...
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Client client;
    @ManyToMany
    @JoinTable(
            name = "NeedSkill",
            joinColumns = @JoinColumn(name = "needId"),
            inverseJoinColumns = @JoinColumn(name = "skillId"))
    Set<Skill> skills;

    public Need (Integer amountNeeded, Integer amountFulfilled, String educationField, Integer yearsExperience,
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
}

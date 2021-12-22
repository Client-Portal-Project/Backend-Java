package com.projectx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

// Will handle the Applicant's information and functionality


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Integer applicantId;
    @OneToOne(mappedBy = "applicant")
    private ResumeFile resume;
    @Column
    private String aboutMe;

    @Column
    private String educationLevel;
    @Column
    private String educationField;
    @Column
    private Boolean workingWithClient;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;



    @OneToOne(cascade = CascadeType.MERGE)
    private User userId;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name = "applicant_skills",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "applicant")
    @JsonIgnore
    private Set<Interview> interviews = new HashSet<>();



}

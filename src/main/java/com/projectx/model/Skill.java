package com.projectx.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillId;


    @Column
    private String skill;


    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Applicant> applicants = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();
}

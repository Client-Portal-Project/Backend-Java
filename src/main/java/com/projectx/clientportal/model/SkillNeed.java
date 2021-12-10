package com.projectx.clientportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Join table to show which skills a Need requires

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SkillNeeds")
public class SkillNeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer skillNeedId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Skill skill;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Need need;
}

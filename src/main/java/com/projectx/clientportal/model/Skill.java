package com.projectx.clientportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    Will handle the list of skills that applicants can have
    and clients can search for
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer skillId;
    @Column
    private String skillName;
}

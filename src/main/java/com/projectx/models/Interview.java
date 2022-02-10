package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

// Will handle scheduling and recording interviews

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer interviewId;
    @Column
    private Date date;
    @ManyToMany
    @JoinTable(
    		name = "InterviewClient",
    		joinColumns = @JoinColumn(name = "interviewId"),
    		inverseJoinColumns = @JoinColumn(name = "clientId"))
    Set<Client> client;
    @ManyToMany
    @JoinTable(
    		name = "InterviewSkill",
    		joinColumns = @JoinColumn(name = "interviewId"),
    		inverseJoinColumns = @JoinColumn(name = "skillId"))
    Set<Skill> skill;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Need need;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Application application;
}

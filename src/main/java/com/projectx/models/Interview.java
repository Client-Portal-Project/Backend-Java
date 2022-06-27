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
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer interviewId;
    @Column
    private Date date;
    @ManyToMany
    @JoinTable(
    		name = "interview_client",
    		joinColumns = @JoinColumn(name = "interview_id"),
    		inverseJoinColumns = @JoinColumn(name = "client_id"))
    Set<Client> client;
    @ManyToMany
    @JoinTable(
    		name = "interview_skill",
    		joinColumns = @JoinColumn(name = "interview_id"),
    		inverseJoinColumns = @JoinColumn(name = "skill_id"))
    Set<Skill> skill;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Need need;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Application application;
}

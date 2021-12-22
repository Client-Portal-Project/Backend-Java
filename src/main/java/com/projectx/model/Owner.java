package com.projectx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Will handle the Owner's information and functionality

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer ownerId;


    @Column
    private String companyName;


    @Column
    private Boolean applicantCanDeclineInterviews;


    @Column
    private Boolean applicantsCanDeclineOffers;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Applicant> applicants = new HashSet<>();


}

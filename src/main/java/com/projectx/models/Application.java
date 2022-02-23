package com.projectx.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/*
    Will handle information and functionality for an applicant's
    application to a client's need
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicationId;
    @Column
    private Date dateOfApplication;
    @Column
    private Integer status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Applicant applicant;
//    Redundant. Already joined to applicant.
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ApplicantOccupation applicantOccupation;
    @ManyToOne(fetch = FetchType.EAGER)
    private Need need;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    // Application(1, null, null, applicant, occupation, need)
    // Application(1, null, 0, applicant, applicantOccupation, need);

    public Application(Integer applicationId, Applicant applicant, ApplicantOccupation applicantOccupation, Need need) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
    }

    public Application(Integer applicationId, Integer status, Applicant applicant, ApplicantOccupation applicantOccupation, Need need, Client client) {
        this.applicationId = applicationId;
        this.status = status;
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
        this.client = client;
    }
}

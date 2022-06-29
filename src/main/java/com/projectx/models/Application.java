package com.projectx.models;

import lombok.*;
import org.springframework.lang.Nullable;

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
@Table(name = "application")

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private int applicationId;
    @Column(name="date_of_application")
    private Date dateOfApplication;
    @Column(name="status")
    private int status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="applicant_occupational_id")
    private ApplicantOccupation applicantOccupation;
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="need_id")
    private Need need;
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    public Application(int applicationId, Applicant applicant, ApplicantOccupation applicantOccupation, Need need) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
    }

    public Application(int applicationId, int status, Applicant applicant, ApplicantOccupation applicantOccupation, Need need, Client client) {
        this.applicationId = applicationId;
        this.status = status;
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
        this.client = client;
    }
    
    public Application(Applicant applicant, ApplicantOccupation applicantOccupation, Need need) {
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
    }

    public Application(int status, Applicant applicant, ApplicantOccupation applicantOccupation, Need need, Client client) {
        this.status = status;
        this.applicant = applicant;
        this.applicantOccupation = applicantOccupation;
        this.need = need;
        this.client = client;
    }
}

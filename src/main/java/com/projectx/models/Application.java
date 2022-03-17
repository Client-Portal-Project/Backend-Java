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
@Table(name = "Applications", indexes = {
        @Index(name = "applicantIndex", columnList = "applicant_applicant_id"),
        @Index(name = "needIndex", columnList = "need_need_id"),
        @Index(name = "clientIndex", columnList = "client_client_id")
})

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
    @Nullable
    private Applicant applicant;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Nullable
    private ApplicantOccupation applicantOccupation;
    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Need need;
    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private Client client;

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

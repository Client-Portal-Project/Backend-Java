package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ApplicantOccupation applicantOccupation;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Need need;
}

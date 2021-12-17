package com.projectx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Application application;
}

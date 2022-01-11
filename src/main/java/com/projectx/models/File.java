package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_type")
    private String type;
    @Lob
    @Column(name = "file_data")
    private byte[] data;
    @Transient
    private Long size;
    @ManyToOne(cascade = CascadeType.ALL)
    private Applicant applicant;

    public File(String name, String type, byte[] data, Applicant applicant) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.applicant = applicant;
        this.size = null;
    }

    public File(String name, String type, Long size, Applicant applicant) {
        this.name = name;
        this.type = type;
        this.data = null;
        this.applicant = applicant;
        this.size = size;
    }
}
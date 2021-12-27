package com.projectx.model;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_type")
    private String type;
    @Lob
    @Column(name = "file_data")
    private byte[] data;
    @ManyToOne(cascade = CascadeType.ALL)
    private Applicant applicant;

    public File(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
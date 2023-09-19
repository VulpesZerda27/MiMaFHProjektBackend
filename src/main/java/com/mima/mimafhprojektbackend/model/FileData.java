package com.mima.mimafhprojektbackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "FileData")
@Data
@NoArgsConstructor
@Builder

public class FileData {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;
    @Lob
    @Column(name = "filedata",length = 1000)
    private byte[] fileData;

}

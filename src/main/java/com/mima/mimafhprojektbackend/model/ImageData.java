package com.mima.mimafhprojektbackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "ImageData")
@Data
@NoArgsConstructor
@Builder

public class ImageData {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;
    @Lob
    @Column(name = "imagedata",length = 1000)
    private byte[] imageData;

}

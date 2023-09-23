package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.ImageData;
import com.mima.mimafhprojektbackend.repository.ImageDataRepository;
import com.mima.mimafhprojektbackend.util.ImageDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository repository;

//    Store and retrieve image from DB
    public  String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageDataUtils.compressImage(file.getBytes())).build());
        if(imageData !=null){
            return "file uploaded successfully : " +file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageDataUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}

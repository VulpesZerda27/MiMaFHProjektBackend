package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.FileData;
import com.mima.mimafhprojektbackend.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileDataService {

    @Autowired
    private FileDataRepository repository;

//    Store and retrieve image from DB
    public  String uploadImage(MultipartFile file) throws IOException {

        FileData fileData = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData( .compressImage (file.getBytes())  ) . build()   );
        if(fileData !=null){
            return "file uploaded successfully : " +file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<FileData> dbFileData = repository.findByName(fileName);
        byte[] images=F.decompressImage(dbFileData.get().getImageData());
        return images;
    }

}

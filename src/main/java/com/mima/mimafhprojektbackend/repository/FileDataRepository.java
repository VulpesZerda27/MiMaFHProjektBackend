package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByName(String fileName);

}


package com.projectx.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.projectx.models.File;
import com.projectx.repositories.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private FileDao fileRepository;

    @Autowired
    public FileService(FileDao fileRepository){
        this.fileRepository = fileRepository;
    }

    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File newFile = new File(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(newFile);
    }

    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
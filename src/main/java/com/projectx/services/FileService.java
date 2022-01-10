package com.projectx.services;

import java.io.IOException;
import java.util.List;

import com.projectx.models.Applicant;
import com.projectx.models.File;
import com.projectx.repositories.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("fileService")
public class FileService {

    private FileDao fileRepository;

    @Autowired
    public FileService(FileDao fileRepository){
        this.fileRepository = fileRepository;
    }

    public File store(MultipartFile file, Applicant applicant) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File newFile = new File(fileName, file.getContentType(), file.getBytes(), applicant);

        return fileRepository.save(newFile);
    }

    public File getFile(int id) {
        return fileRepository.findById(id).get();
    }

    public List<File> getAllFiles(Applicant applicant) {
        return fileRepository.findByApplicant(applicant.getApplicantId());
    }
}
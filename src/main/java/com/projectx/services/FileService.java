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

    /**
     * Creates a file object within the database with an applicant and a multipart file object
     *
     * @param file multipart file object
     * @param applicant applicant object
     * @return file object
     * @throws IOException
     */
    public File store(MultipartFile file, Applicant applicant) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File newFile = new File(fileName, file.getContentType(), file.getBytes(), applicant);

        return fileRepository.save(newFile);
    }

    /**
     * Gets a file object from the database with its id
     *
     * @param id id of the file
     * @return file object if found, null otherwise
     */
    public File getFile(int id) {
        return fileRepository.findById(id).get();
    }

    /**
     * Gets a List of files attached to an applicant object
     *
     * @param applicant applicant object
     * @return List of file objects
     */
    public List<File> getAllFiles(Applicant applicant) {
        return fileRepository.findByApplicant_ApplicantId(applicant.getApplicantId());
    }
}
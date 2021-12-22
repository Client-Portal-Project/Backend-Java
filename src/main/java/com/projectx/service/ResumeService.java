package com.projectx.service;


import java.io.IOException;
import java.util.stream.Stream;


import com.projectx.model.ResumeFile;
import com.projectx.repository.ResumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ResumeService {


    private ResumeRepo resumeRepository;

    @Autowired
    public ResumeService(ResumeRepo resumeRepository){
        this.resumeRepository = resumeRepository;
    }

    public ResumeFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ResumeFile ResumeFile = new ResumeFile(fileName, file.getContentType(), file.getBytes());

        return resumeRepository.save(ResumeFile);
    }

    public ResumeFile getFile(String id) {
        return resumeRepository.findById(id).get();
    }

    public Stream<ResumeFile> getAllFiles() {
        return resumeRepository.findAll().stream();
    }
}
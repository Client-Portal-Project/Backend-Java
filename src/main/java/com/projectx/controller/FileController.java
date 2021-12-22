package com.projectx.controller;



import com.projectx.DTOs.ResponseFile;
import com.projectx.DTOs.ResponseMessage;
import com.projectx.model.ResumeFile;
import com.projectx.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController("fileController")
@RequestMapping(value = "api")
public class FileController {

    private final ResumeService storageService;

    @Autowired
    public FileController(ResumeService storageService){
        this.storageService = storageService;
    }

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
        String message = "";
        try{
            storageService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename() + "!";

            return ResponseEntity.status(200).body(new ResponseMessage(message));
        } catch (IOException e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.badRequest().body(new ResponseMessage(message));
        }

    }



    @CrossOrigin
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @CrossOrigin
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        ResumeFile resumeFile = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resumeFile.getName() + "\"")
                .body(resumeFile.getData());
    }

}
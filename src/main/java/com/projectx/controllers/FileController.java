package com.projectx.controllers;

import com.projectx.models.Applicant;
import com.projectx.models.File;
import com.projectx.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("fileController")
@RequestMapping("file")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<Boolean> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestBody Applicant applicant){
        try{
            fileService.store(file, applicant);
            return ResponseEntity.ok().body(true);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping
    public ResponseEntity<List<File>> getListFiles(@RequestBody Applicant applicant) {
        List<File> files = fileService.getAllFiles(applicant);
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        File file = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
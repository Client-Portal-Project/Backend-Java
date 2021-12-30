package com.projectx.controllers;

import com.projectx.DTOs.ResponseFile;
import com.projectx.models.File;
import com.projectx.service.FileService;
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
@RequestMapping(value = "file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Boolean> uploadFile(@RequestParam("file") MultipartFile file){
        try{
            fileService.store(file);
            return ResponseEntity.status(200).body(true);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/file")
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
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        System.out.println(id);
        File file = fileService.getFile(id);
        System.out.println(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
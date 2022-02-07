package com.projectx.controllers;

import com.projectx.models.Applicant;
import com.projectx.models.File;
import com.projectx.services.ApplicantService;
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
    @Autowired
    private ApplicantService applicantService;

    /**
     * Creates a new file in the database through the file service class
     *
     * @param file multipart file in the request parameter
     * @param applicant applicant object in the request body
     * @return a http response with a boolean in a {@link ResponseEntity} that
     *      contains an ok request and true, otherwise false and a not found status code
     */
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

    /**
     * Gets all the files under the applicant object in a List
     *
     * @param applicant applicant object in the request body
     * @return a http response with a List of file objects in a {@link ResponseEntity} that
     *      contains an ok request
     */
    @GetMapping
    public ResponseEntity<List<File>> getListFiles(@RequestBody Applicant applicant) {
        List<File> files = fileService.getAllFiles(applicant);
        files.stream().forEach(f -> {
                f.setSize((long) f.getData().length);
                f.setData(null);
            });
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    /**
     * Gets a file by its id number
     *
     * @param id id of the file in the path variable
     * @return a http response with a byte array of the file in a {@link ResponseEntity} that
     *      contains an ok request
     */
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        File file = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
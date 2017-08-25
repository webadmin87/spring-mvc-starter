package ru.rzncenter.webcore.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.rzncenter.webcore.service.FileUploader;
import ru.rzncenter.webcore.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.rzncenter.webcore.web.response.FileUploadResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Контроллер обрабатывающий загрузку и удаление файлов
 */
@RestController
@RequestMapping(value="/admin/")
public class FileUploadController {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private FileUploader fileUploader;

    @RequestMapping(value="/upload/", method= RequestMethod.GET)
    public String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    /**
     * Загрузка файла 
     * @param files
     * @return
     */
    @RequestMapping(value="/upload/", method=RequestMethod.POST)
    public ResponseEntity<FileUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile[] files) {
        HttpHeaders headers = new HttpHeaders();
        List<String> success = new ArrayList<>();
        List<String> error = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = fileUploader.handleFileUpload(file);
            if (StringUtils.isNotBlank(path)) {
                success.add(path);
            } else {
                error.add(file.getOriginalFilename());
            }
        }
        return new ResponseEntity<>(new FileUploadResponse(success, error), headers, HttpStatus.OK);
    }

    /**
     * Удаление файла 
     * @param path
     * @return
     */
    @RequestMapping(value="/remove-file/", method=RequestMethod.DELETE)
    public ResponseEntity<String> handleFileRemove(@RequestParam("path") String path) {
        boolean res = fileUtils.deleteFileByWebPath(path);
        HttpHeaders headers = new HttpHeaders();
        if(res) {
            return new ResponseEntity<>("OK", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error deleting file", headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Загрузка файла по url адресу
     * @param url
     * @return
     */
    @RequestMapping(value="/upload-by-url/", method=RequestMethod.POST)
    public ResponseEntity<String> handleFileUploadFromUrl(@RequestParam String url) {
        HttpHeaders headers = new HttpHeaders();
        String path = fileUploader.handleFileUploadFromUrl(url);
        if(StringUtils.isNotBlank(path)) {
            return new ResponseEntity<>(path, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad request", headers, HttpStatus.BAD_REQUEST);
        }
    }

}

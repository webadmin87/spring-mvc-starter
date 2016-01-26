package ru.rzncenter.webcore.controllers;


import ru.rzncenter.webcore.service.FileUploader;
import ru.rzncenter.webcore.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;


/**
 * Контроллер обрабатывающий загрузку и удаление файлов
 */
@Controller
@RequestMapping(value="/admin/")
public class FileUploadController {

    @Autowired
    ServletContext context;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    FileUploader fileUploader;

    @RequestMapping(value="/upload/", method= RequestMethod.GET)
    public @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    /**
     * Загрузка файла 
     * @param file
     * @return
     */
    @RequestMapping(value="/upload/", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){

        HttpHeaders headers = new HttpHeaders();

        if (!file.isEmpty()) {

            String path = fileUploader.handleFileUpload(file);

            if(path != null)
                return new ResponseEntity<>(path, headers, HttpStatus.OK);
            else
                return new ResponseEntity<>("You failed to upload file", headers, HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            return new ResponseEntity<>("You failed to upload file because the file was empty.", headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление файла 
     * @param path
     * @return
     */
    @RequestMapping(value="/remove-file/", method=RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<String> handleFileRemove(@RequestParam("path") String path) {
        
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
    public @ResponseBody ResponseEntity<String> handleFileUploadFromUrl(@RequestParam String url) {

        HttpHeaders headers = new HttpHeaders();

        String path = fileUploader.handleFileUploadFromUrl(url);

        if(path != null)
            return new ResponseEntity<>(path, headers, HttpStatus.OK);
        else
            return new ResponseEntity<>("Bad request", headers, HttpStatus.BAD_REQUEST);

    }

}

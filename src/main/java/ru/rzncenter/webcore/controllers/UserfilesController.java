package ru.rzncenter.webcore.controllers;

import ru.rzncenter.webcore.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anton on 22.01.15.
 */

@Controller
public class UserfilesController {

    @Autowired
    FileUtils fileUtils;

    @RequestMapping(value = "/userfiles/**" , method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(HttpServletRequest req) {
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        String filePath = fileUtils.webToServerPath(uri);
        FileSystemResource res = new FileSystemResource(filePath);
        if(res.exists()) {
            return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

}

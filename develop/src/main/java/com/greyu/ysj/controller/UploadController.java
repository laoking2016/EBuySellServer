package com.greyu.ysj.controller;

import com.greyu.ysj.config.Constants;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.storage.StorageService;
import com.greyu.ysj.utils.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 18:56 2018/5/7.
 */
@RestController
public class UploadController {

	private final StorageService storageService;
	
	@Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@RequestMapping(value = "/api/v1/fileUpload", method = RequestMethod.POST)
	public ResponseEntity<ResultModel> handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, IOException {

        String filename = storageService.store(file);
        
        return new ResponseEntity<>(ResultModel.ok(filename), HttpStatus.OK);
    }
	
	@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}

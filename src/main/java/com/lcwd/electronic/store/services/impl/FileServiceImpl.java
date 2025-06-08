package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exception.BadApiRequest;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private Logger logeer = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String UploadFile(MultipartFile file, String path) throws IOException {

        String originalFileName = file.getOriginalFilename();

        logeer.info("File Name { }"+originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String finalNameWithExcetion = fileName+extension;
        String fullFileName = path+ File.separator+finalNameWithExcetion;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullFileName));
            return fileName;
        }else{
               throw new BadApiRequest("File With This"+extension+"Not Allowed");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String filePath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }
}

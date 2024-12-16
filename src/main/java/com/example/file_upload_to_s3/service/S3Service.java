package com.example.file_upload_to_s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    void uploadToS3(MultipartFile file) throws IOException;
}

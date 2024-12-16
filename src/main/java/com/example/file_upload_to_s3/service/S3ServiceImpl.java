package com.example.file_upload_to_s3.service;

import com.example.file_upload_to_s3.exception.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Set;

@Service
public class S3ServiceImpl implements S3Service {
    private static final Logger log = LoggerFactory.getLogger(S3ServiceImpl.class);
    private static final long MAX_FILE_SIZE = 1024 * 1024; // 1MB
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png");

    private final S3Client s3Client;
    private final String bucketName;

    public S3ServiceImpl(S3Client s3Client, @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void uploadToS3(MultipartFile file) throws IOException {
        validateFile(file);

        try (var inputStream = file.getInputStream()) {
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            log.info("File '{}' uploaded successfully to S3", file.getOriginalFilename());
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file to S3", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("File is empty");
        }
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileUploadException("Invalid file type. Only JPEG and PNG are allowed");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileUploadException("File size exceeds the 1MB limit");
        }
    }
}

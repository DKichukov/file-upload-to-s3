package com.example.file_upload_to_s3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.Map;

@Service
public class S3ServiceImpl implements S3Service {

    private static final long MAX_FILE_SIZE_MB = 1024 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3ServiceImpl.class);

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadToS3(String fileName, InputStream inputStream) {

        if (s3Client == null) {
            LOGGER.error("S3 client is not initialized. Please check your AWS configuration.");
            return;
        }

        try {
            long fileSize = inputStream.available();
            if (fileSize <= 0) {
                LOGGER.error("Input stream is empty or null. Upload aborted.");
                return;
            }

            if (fileSize > MAX_FILE_SIZE_MB) {
                LOGGER.error("File size exceeds the maximum allowed limit of {} MB. Upload aborted.", MAX_FILE_SIZE_MB / (1024 * 1024));
                return;
            }

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType("image/jpeg")
                    .metadata(Map.of("fileName", fileName))
                    .build();

            // Upload the file
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));
            LOGGER.info("File uploaded successfully to S3: {}", fileName);

        } catch (Exception e) {
            LOGGER.error("Error uploading file to S3: ", e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                LOGGER.warn("Failed to close input stream: ", e);
            }
        }
    }


}

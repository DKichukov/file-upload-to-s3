package com.example.file_upload_to_s3.service;

import java.io.InputStream;

public interface S3Service {

    /**
     * Uploads a file to the configured S3 bucket using an InputStream.
     *
     * @param fileName    the name of the file being uploaded
     * @param inputStream the InputStream containing the file data
     */
    public void uploadToS3(String fileName, InputStream inputStream);

}

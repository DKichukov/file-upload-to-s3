package com.example.file_upload_to_s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {

    /**
     * Uploads a file to an S3 bucket.
     *
     * @param file the file to be uploaded, represented as a {@link MultipartFile}.
     * @throws IOException if an error occurs during file processing or uploading.
     */
    void uploadToS3(MultipartFile file) throws IOException;

    /**
     * Retrieves an image as an {@link InputStream} based on the given file name.
     *
     * @param fileName the name of the file to retrieve, including its extension.
     * @return an {@link InputStream} to read the image data.
     * @throws IOException if an error occurs while accessing or reading the file.
     */
    InputStream getImageByName(String fileName) throws IOException;

    /**
     * Deletes an image file based on the given file name.
     *
     * @param fileName the name of the file to be deleted, including its extension.
     */
    void deleteImage(String fileName);

}

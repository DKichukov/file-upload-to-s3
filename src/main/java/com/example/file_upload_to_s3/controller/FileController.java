package com.example.file_upload_to_s3.controller;


import com.example.file_upload_to_s3.service.S3Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class FileController {

    private final S3Service s3Service;

    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/file-upload")
    public String fileUpload() {
        return "form";
    }

    @PostMapping("/file-upload")
    public String saveFiles(Model model,
                            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            model.addAttribute("error", "No file selected for upload.");
            return "form";
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            model.addAttribute("error", "Invalid file name.");
            return "form";
        }

        try {
            if (!Objects.equals(file.getContentType(), "image/jpeg") && !Objects.equals(file.getContentType(), "image/png")) {
                model.addAttribute("error", "Invalid file type. Only JPEG and PNG are allowed.");
                return "form";
            }

            if (file.getSize() > 1024 * 1024) { // 1MB
                model.addAttribute("error", "File size exceeds the 1MB limit.");
                return "form";
            }

            s3Service.uploadToS3(fileName, file.getInputStream());
            model.addAttribute("success", "File uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading file to S3: " + e.getMessage());
        }

        return "form";
    }

}

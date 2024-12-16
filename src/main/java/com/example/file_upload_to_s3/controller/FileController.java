package com.example.file_upload_to_s3.controller;


import com.example.file_upload_to_s3.exception.FileUploadException;
import com.example.file_upload_to_s3.service.S3Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    private final S3Service s3Service;

    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/file-upload")
    public String showUploadForm() {
        return "form";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            s3Service.uploadToS3(file);
            model.addAttribute("success", "File uploaded successfully!");
        } catch (FileUploadException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred");
        }
        return "form";
    }
}

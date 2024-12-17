package com.example.file_upload_to_s3.controller;

import com.example.file_upload_to_s3.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    public String showUploadForm() {
        return "form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("uploadError", "Please select a file to upload");
                return "form";
            }
            s3Service.uploadToS3(file);
            model.addAttribute("uploadSuccess", "File '" + file.getOriginalFilename() + "' was uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("uploadError", "Failed to upload file: " + e.getMessage());
        }
        return "form";
    }

    @GetMapping("/view")
    public ResponseEntity<InputStreamResource> viewImage(@RequestParam String fileName, Model model) {
        try {
            InputStreamResource resource = new InputStreamResource(s3Service.getImageByName(fileName));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete")
    public String deleteImage(@RequestParam String fileName, Model model) {
        try {
            if (fileName.trim().isEmpty()) {
                model.addAttribute("deleteError", "Please enter a file name to delete");
                return "form";
            }
            s3Service.deleteImage(fileName);
            model.addAttribute("deleteSuccess", "File '" + fileName + "' was deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("deleteError", "Failed to delete file: " + e.getMessage());
        }
        return "form";
    }
}

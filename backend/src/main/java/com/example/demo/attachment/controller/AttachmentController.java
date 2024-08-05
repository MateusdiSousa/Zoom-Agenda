package com.example.demo.attachment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.attachment.dto.AttachmentDto;
import com.example.demo.attachment.services.AttachmentService;
import com.example.demo.attachment.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @Autowired FileService fileService;

    @PostMapping()
    public ResponseEntity<String> saveAttachment(@ModelAttribute AttachmentDto dto) {
        String response = fileService.storeFile(dto.file(),dto.meetingId());
        return ResponseEntity.ok(response); 
    }
    
}

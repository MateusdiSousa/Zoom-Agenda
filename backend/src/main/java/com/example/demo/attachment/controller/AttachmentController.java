package com.example.demo.attachment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.attachment.domain.AttachmentDto;
import com.example.demo.attachment.services.AttachmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("attachment")
public class AttachmentController {
    private AttachmentService attachmentService;

    @PostMapping()
    public ResponseEntity<String> saveAttachment(@RequestBody AttachmentDto dto) {
        String response = attachmentService.SaveAttachment(dto.meetingId(), dto.files());
        return ResponseEntity.ok(response); 
    }
    
}

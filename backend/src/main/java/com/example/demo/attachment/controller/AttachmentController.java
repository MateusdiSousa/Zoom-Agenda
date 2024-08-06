package com.example.demo.attachment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.attachment.dto.AttachmentDto;
import com.example.demo.attachment.services.AttachmentService;
import com.example.demo.attachment.services.FileService;
import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.services.MeetingServices;

import java.io.File;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private MeetingServices meetingServices;

    @PostMapping()
    public ResponseEntity<String> saveAttachment(@ModelAttribute AttachmentDto dto) {
        // for (MultipartFile file : dto.getFiles()) {
        //     attachmentService.SaveAttachment(dto.getMeetingId(), file);
        //     System.out.println(file.getOriginalFilename());
        // }

        List<MultipartFile> files = dto.getFiles();
        for (MultipartFile file : files) {
            attachmentService.SaveAttachment(dto.getMeetingId(), file);
        }

        return ResponseEntity.ok("Arquivos salvos"); 
    }

    @GetMapping("/{meetingId}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String meetingId, @PathVariable String fileName) {
        Resource file = attachmentService.loadAsResource(meetingId, fileName);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        System.out.println(file.getFilename());

        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename()+"\"")
        .body(file);
    }

}

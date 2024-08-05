package com.example.demo.attachment.dto;

import org.springframework.web.multipart.MultipartFile;

public record AttachmentDto(
    String meetingId,
    MultipartFile file
) {}

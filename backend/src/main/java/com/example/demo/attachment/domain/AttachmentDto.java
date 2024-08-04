package com.example.demo.attachment.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record AttachmentDto(
    String meetingId,
    List<MultipartFile> files
) {}

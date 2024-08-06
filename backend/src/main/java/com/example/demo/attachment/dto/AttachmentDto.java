package com.example.demo.attachment.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private String meetingId;
    private List<MultipartFile> files;
}

package com.example.demo.attachment.services;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.attachment.domain.Attachment;
import com.example.demo.attachment.domain.AttachmentRepository;
import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.services.MeetingServices;

import jakarta.transaction.Transactional;

@Service
public class AttachmentService {
    @Autowired
    private FileService fileService;

    @Autowired
    private AttachmentRepository repository;

    @Autowired
    private MeetingServices meetingServices;

    public String SaveAttachment(String meetingId, MultipartFile file) {
        Meeting meeting = this.meetingServices.getOneMeetingByIdZoom(meetingId);

        if (meeting == null) {
            throw new IllegalArgumentException("Meeting not found for id: " + meetingId);
        }

        try {
            String name = fileService.storeFile(file, meeting.getId());
            String url = "http://localhost:8080/attachment/" + meeting.getId() + "/" + name;
            Attachment attachment = new Attachment();
            attachment.setMeeting(meeting);
            attachment.setFilename(name);
            attachment.setFilelenght(String.valueOf(file.getSize()));
            attachment.setFiletype(file.getContentType().split("/")[1]);
            attachment.setUrl(url);

            repository.save(attachment);

            return "Files saved successfully";
        } catch (Exception e) {
            throw new  Error(e.getMessage());
        }
    }

    public Path load(String meetingId, String fileName) {
        String rootPath = "anexos/" + meetingId+ "/"+fileName;
        Path dest = Paths.get(rootPath);
        return dest;
    }

    public Resource loadAsResource(String meetingId, String fileName) {
        try {
            Path file = load(meetingId, fileName).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not read file: " + fileName);
            }
        } catch (Exception e) {
            throw new Error("Could not read file: " + fileName, e);
        }
    }
}

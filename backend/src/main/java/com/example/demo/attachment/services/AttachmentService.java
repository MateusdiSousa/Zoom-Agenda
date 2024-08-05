package com.example.demo.attachment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.attachment.domain.Attachment;
import com.example.demo.attachment.domain.AttachmentRepository;
import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.services.MeetingServices;

@Service
public class AttachmentService {
    @Autowired
    private FileService fileService;

    @Autowired
    private MeetingServices meetingServices;

    @Autowired
    private AttachmentRepository repository;

    public String SaveAttachment(String meetingId, MultipartFile file) {
        Meeting meeting = meetingServices.getOneMeeting(meetingId);
        try {

            String name = fileService.storeFile(file, meetingId);
            // String url = "http://localhost:8080/attachment/"+meetingId+"/"+name;
            // Attachment attachment = new Attachment();
            // attachment.setFileName(name);
            // attachment.setMeeting(meeting);
            // attachment.setFilelenght(String.valueOf(file.getSize()));
            // attachment.setFiletype(file.getContentType());
            // attachment.setUrl(url);
            // repository.save(attachment);
            return "Files saved successfully";
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}

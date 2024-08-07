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

    public static void copyAndRename(File source, File destination) throws IOException {
        if (!source.exists()) {
            throw new IOException("Source file does not exist!");
        }

        if (destination.exists()) {
            throw new IOException("Destination file already exist!");
        }
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static String formatFilenameWithId(String fileName ){
        int lastIndex = fileName.lastIndexOf(".");
        String filenameWithoutId = fileName.split("-")[0] + fileName.substring(lastIndex);
        return filenameWithoutId;
    }

    @Transactional
    public ResponseEntity<String> deleteAttachment(String attachmentId) {
        Optional<Attachment> response = repository.findById(attachmentId);

        if (!response.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attachment not found");
        }

        Attachment attachment = response.get();
        String url = this.root + attachment.getMeetingId() + "/" + attachment.getFilename();
        File dir = new File(this.root + attachment.getMeetingId());
        File file = new File(url);
         
        try {
            repository.delete(attachment);
            
            if (file.exists() && !file.delete()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the file");
            }

            if (dir.exists() && dir.isDirectory() && dir.listFiles().length == 0 && !dir.delete() ) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the directory");
            }

            return ResponseEntity.ok("File deleted sucessfully")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting attachment: " + e.getMessage());
        }
    }
}

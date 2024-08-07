package com.example.demo.attachment.domain;

import com.example.demo.meeting.domain.Meeting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attachment")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String filename;

    private String filetype;

    private String filelenght;

    private String url;

    public String getFilelenght() {
        return filelenght;
    }

    public String getFilename() {
        return filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMeetingId(){
        return meeting.getId();
    }
    @ManyToOne
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;
}

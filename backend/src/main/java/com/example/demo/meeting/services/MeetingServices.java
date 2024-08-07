package com.example.demo.meeting.services;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.attachment.services.AttachmentService;
import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.domain.MeetingRepository;
import com.example.demo.meeting.dto.MeetingDto;

@Service
public class MeetingServices {
	private static final String root = "anexos/";
	@Autowired
	private MeetingRepository meetingRepository;

	public void SaveMeeting(MeetingDto dto) {
		try {
			Meeting meeting = new Meeting(dto);
			this.meetingRepository.save(meeting);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ResponseEntity<String> UpdateMeeting(MeetingDto dto) {
		Optional<Meeting> response = meetingRepository.getByMeetingId(dto.getMeeting_id());
		if (response.isPresent()) {
			Meeting meeting = response.get();
			meeting.updateMeeting(dto);
			meetingRepository.save(meeting);
			return ResponseEntity.status(204).build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<List<Meeting>> getAllMeeting() {
		try {
			List<Meeting> meetings = this.meetingRepository.findAll();
			return ResponseEntity.ok(meetings);
		} catch (DataAccessException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	public ResponseEntity<String> deleteMeeting(String id) {
		Optional<Meeting> response = meetingRepository.getByMeetingId(id);
		if (!response.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting not found!");
		}
		Meeting meeting = response.get();
		try {
			meetingRepository.delete(meeting);
			DeleteMeetingAttachment(id);
			return ResponseEntity.status(HttpStatus.OK).body("Meeting deleted successfully");
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}

	public Meeting getOneMeeting(String id){
		try {
			Optional<Meeting> response = meetingRepository.findById(id);
			Meeting meeting = response.get();
			return meeting;	
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}

	public Meeting getOneMeetingByIdZoom(String id){
		try {
			Optional<Meeting> response = meetingRepository.getByMeetingId(id);
			Meeting meeting = response.get();
			return meeting;	
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}

	  public boolean DeleteMeetingAttachment(String meetingId){
        String uri = this.root + meetingId;
        File dir = new File(uri);
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        
        return dir.delete();
    }
}

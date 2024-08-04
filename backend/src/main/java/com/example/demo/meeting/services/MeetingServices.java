package com.example.demo.meeting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.domain.MeetingDto;
import com.example.demo.meeting.domain.MeetingRepository;

@Service
public class MeetingServices {
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
		try {
			Optional<Meeting> response = meetingRepository.getByMeetingId(id);

			if (response.isPresent()) {
				meetingRepository.delete(response.get());
				return ResponseEntity.ok("Deletado com sucesso");
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
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
}

package com.example.demo.meeting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
	
	public ResponseEntity<List<Meeting>> getAllMeeting(){
		try {
			List<Meeting> meetings = this.meetingRepository.findAll();
			return ResponseEntity.ok(meetings);
		} catch (DataAccessException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}

package com.example.demo.meeting.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.meeting.domain.Meeting;
import com.example.demo.meeting.domain.ZoomMeetingDto;
import com.example.demo.meeting.services.MeetingServices;
import com.example.demo.meeting.services.ZoomServices;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
	
	@Autowired
	private ZoomServices zoomService;
	
	@Autowired 
	private MeetingServices meetingService;
	
	@PostMapping("/token/{code}")
	public ResponseEntity<String> getToken(@PathVariable String code)  {
		return zoomService.getToken(code);
	}
	
	@PostMapping("/schedule")
	public String scheduleMeeting(@RequestBody ZoomMeetingDto data, @RequestHeader("authorization") String token ) {
		return zoomService.ScheduleMeetingZoom(data, token);
	}
	
	@GetMapping
	public ResponseEntity<List<Meeting>> getAllMeeting(){
		return meetingService.getAllMeeting();
	}
}

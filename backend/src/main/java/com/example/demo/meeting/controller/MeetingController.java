package com.example.demo.meeting.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.meeting.domain.ZoomMeetingDto;
import com.example.demo.meeting.services.MeetingServices;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
	
	@Autowired
	private MeetingServices meetingService;
	
	@PostMapping("/token/{code}")
	public ResponseEntity<String> getToken(@PathVariable String code)  {
		return meetingService.getToken(code);
	}
	
	@PostMapping("/schedule")
	public String scheduleMeeting(@RequestBody ZoomMeetingDto data, @RequestHeader("authorization") String token ) {
		return meetingService.ScheduleMeetingZoom(data, token);
	}
}

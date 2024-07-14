package com.example.demo.meeting.domain;

import java.sql.Date;

import org.json.JSONObject;

public record MeetingDto(
	String id,
	String title,
	String meeting,
	Date start_time,
	Integer duration_minutes, 
	String join_url,
	String requester,
	JSONObject participants,
	String meeting_minutes
) {};

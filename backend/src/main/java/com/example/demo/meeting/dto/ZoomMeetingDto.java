package com.example.demo.meeting.dto;

public record ZoomMeetingDto(
		String topic,
		String agenda,
		String start_time,
		Integer duration,
		String timezone,
		Integer type,
		ZoomMeetingSettings settings
) {}

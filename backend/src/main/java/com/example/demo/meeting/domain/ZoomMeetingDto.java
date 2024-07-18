package com.example.demo.meeting.domain;
import java.time.ZonedDateTime;

public record ZoomMeetingDto(
		String topic,
		String agenda,
		String start_time,
		Integer duration,
		String timezone,
		Integer type,
		ZoomMeetingSettings settings
) {}

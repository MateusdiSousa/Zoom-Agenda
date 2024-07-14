package com.example.demo.meeting.domain;

import java.sql.Date;

public record ZoomMeetingDto(
		String topic,
		String agenda,
		Date start_time,
		Integer duration,
		String timezone,
		Integer type,
		ZoomMeetingSettings settings
) {}

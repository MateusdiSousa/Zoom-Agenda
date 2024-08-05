package com.example.demo.meeting.dto;

public record ZoomMeetingSettings(
		Boolean host_video,
		Boolean participant_video,
		Boolean join_before_host,
		Boolean mute_upon_entry,
		Boolean watermark,
		Boolean use_pmi,
		Integer approval_type,
		String audio,
		String auto_recording	
) {}

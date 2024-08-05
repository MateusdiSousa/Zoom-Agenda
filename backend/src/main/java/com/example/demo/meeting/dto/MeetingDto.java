package com.example.demo.meeting.dto;

import org.json.JSONObject;

public class MeetingDto{
	private String topic;
	private String agenda;
	private String start_time;
	private int duration_minutes; 
	private String join_url;
	private String requester;
	private JSONObject participants;
	private String meeting_id;
	
	public MeetingDto(String topic, String agenda, String start_time, int duration, String join_url, String requester, JSONObject participants, String meeting_id) {
		this.topic = topic;
		this.agenda = agenda;
		this.start_time = start_time;
		this.duration_minutes = duration;
		this.join_url = join_url;
		this.requester = requester;
		this.participants = participants;
		this.meeting_id = meeting_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public int getDuration_minutes() {
		return duration_minutes;
	}

	public void setDuration_minutes(int duration_minutes) {
		this.duration_minutes = duration_minutes;
	}

	public String getJoin_url() {
		return join_url;
	}

	public void setJoin_url(String join_url) {
		this.join_url = join_url;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public JSONObject getParticipants() {
		return participants;
	}

	public void setParticipants(JSONObject participants) {
		this.participants = participants;
	}

	public String getMeeting_id() {
		return meeting_id;
	}

	public void setMeeting_id(String meeting_id) {
		this.meeting_id = meeting_id;
	}
	
	
};

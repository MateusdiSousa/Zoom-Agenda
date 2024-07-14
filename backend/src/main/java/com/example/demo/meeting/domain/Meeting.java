package com.example.demo.meeting.domain;

import java.sql.Date;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Meeting")
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String title;

	private String meeting;
	
	private Date start_time;
	
	private Integer duration_minutes;
	
	private String join_url;
	
	private String requester;
	
	private Object participants;
	
	private String meeting_minutes;
	
	public Meeting(MeetingDto dto) {
		this.title = dto.title();
		this.meeting = dto.title();
		this.start_time = dto.start_time();
		this.duration_minutes = dto.duration_minutes();
		this.join_url = dto.join_url();
		this.requester = dto.requester();
		this.participants = dto.participants();
		this.meeting_minutes = dto.meeting_minutes();
	}

 	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Integer getDuration_minutes() {
		return duration_minutes;
	}

	public void setDuration_minutes(Integer duration_minutes) {
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

	public Object getParticipants() {
		return participants;
	}

	public void setParticipants(JSONObject participants) {
		this.participants = participants;
	}

	public String getMeeting_minutes() {
		return meeting_minutes;
	}

	public void setMeeting_minutes(String meeting_minutes) {
		this.meeting_minutes = meeting_minutes;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}

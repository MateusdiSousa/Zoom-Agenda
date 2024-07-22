package com.example.demo.meeting.domain;

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

	private String topic;

	private String agenda;
	
	private String start_time;
	
	private Integer duration_minutes;
	
	private String join_url;
	
	private String requester;
	
	private Object participants;
	
	private String meeting_id;
	
	
	public Meeting(MeetingDto dto) {
		this.agenda = dto.getAgenda();
		this.topic = dto.getTopic();
		this.start_time = dto.getStart_time();
		this.duration_minutes = dto.getDuration_minutes();
		this.join_url = dto.getJoin_url();
		this.requester = dto.getRequester();
		this.participants = dto.getParticipants();
		this.meeting_id = dto.getMeeting_id();
	}
	
	public Meeting() {}

 	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAgenda() {
		return this.agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
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

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getMeeting_id() {
		return meeting_id;
	}

	public void setMeeting_id(String meeting_id) {
		this.meeting_id = meeting_id;
	}
	
}

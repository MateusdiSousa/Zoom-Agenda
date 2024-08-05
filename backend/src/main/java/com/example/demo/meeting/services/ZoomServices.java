package com.example.demo.meeting.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.meeting.dto.MeetingDto;
import com.example.demo.meeting.dto.ZoomMeetingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ZoomServices {
	@Value("${ZOOM_CLIENT_ID}")
	private String client_id;
	
	@Value("${ZOOM_CLIENT_SECRET}")
	private String client_secret;

	@Value("${REDIRECT_URI}")
	private String redirect_uri;
	
	@Autowired
	private MeetingServices meetingService;


	public String generateBasicAuthHeader() {
		String credentials = getClientId() + ":" + getClientSecret();
		String encoded_credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
		return "Basic " + encoded_credentials;
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public ResponseEntity<String> getToken(String code) {
		String url = "https://zoom.us/oauth/token";
		HttpClient client = HttpClient.newHttpClient();
		String credential = generateBasicAuthHeader();

		Map<Object, Object> body = new HashMap<Object, Object>();
		body.put("grant_type", "authorization_code");
		body.put("code", code);
		body.put("redirect_uri", this.redirect_uri);

		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(url))
					.header("Content-Type", "application/x-www-form-urlencoded").header("Authorization", credential)
					.POST(buildFormDataFromMap(body)).build();
			try {
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				return ResponseEntity.ok(response.body());
			} catch (Exception e) {
				return ResponseEntity.ok("Erro 1");
			}
		} catch (Exception e) {
			return ResponseEntity.ok("Erro 2");
		}
	}

	public String ScheduleMeetingZoom(ZoomMeetingDto dto, String token) {
		String url = "https://api.zoom.us/v2/users/me/meetings";

		HttpClient client = HttpClient.newHttpClient();

		try {
			String requestBody = objectMapper.writeValueAsString(dto);			
			
			HttpRequest request = HttpRequest.newBuilder(URI.create(url))
					.header("Authorization", token)
					.header("User-Agent", "Zoom-api-Jwt-Request")
					.header("Content-Type", "application/json")
					.POST(BodyPublishers.ofString(requestBody)).build();
			try {
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				String responseBody = response.body();
				JsonObject json = (JsonObject) JsonParser.parseString(responseBody);
				
				MeetingDto meeting = this.convertJsonToMeetingDto(json);
				
				meetingService.SaveMeeting(meeting);
				
				return response.body();

			} catch (Exception e) {
				return e.getMessage();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String DeleteMeeting(String id, String token) {
		
		String url = "https://api.zoom.us/v2/meetings/"+id;

		HttpClient client = HttpClient.newHttpClient();
		
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(url))
			.header("Authorization", token)
			.header("User-Agent", "Zoom-api-Jwt-Request")
			.header("Content-Type", "application/json")
			.DELETE().build();
			
			try { 
				HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
				meetingService.deleteMeeting(id);
				return response.body();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	public ResponseEntity<String> UpdateMeetingZoom(ZoomMeetingDto meeting, String token, String id){
		String url = "https://api.zoom.us/v2/meetings/"+id;
		HttpClient client = HttpClient.newHttpClient();
		try {
			String requestBody = objectMapper.writeValueAsString(meeting);
			URI uri = URI.create(url);
			System.out.println(uri);

			HttpRequest request = HttpRequest.newBuilder(uri)
			.header("Authorization", "Bearer "+token)
			.header("User-Agent", "Zoom-api-Jwt-Request")
			.header("Content-Type", "application/json")
			.method("PATCH", BodyPublishers.ofString(requestBody)).build();

			System.out.println(request);
			try {
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				if (response.statusCode() == 204) {
					String time = this.convertDateTimeBrasilToUTC(meeting.start_time().substring(0,16));
					System.out.println(time);
					MeetingDto dto = new MeetingDto(meeting.topic(), meeting.agenda(), time, meeting.duration(), "", "",null, id);
					return meetingService.UpdateMeeting(dto);
				}		
				
				if (response.statusCode() == 401) {
					return ResponseEntity.of(ProblemDetail.forStatus(401)).build();
				}

				return ResponseEntity.notFound().build();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return ResponseEntity.badRequest().build();
			}

		} catch (Exception e) {
			return ResponseEntity.ofNullable(e.getMessage());
		}
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		String formData = data.entrySet().stream()
				.map(entry -> URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8) + "="
						+ URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));

		return BodyPublishers.ofString(formData);
	}

	public MeetingDto convertJsonToMeetingDto(JsonObject json){
		String topic = json.get("topic").getAsString();
		String agenda = json.get("agenda").getAsString();
		String requester = json.get("host_email").getAsString();
		int duration = json.get("duration").getAsInt();
		String join_url = json.get("join_url").getAsString();
		String start_time = json.get("start_time").getAsString().substring(0,16);
		String meeting_id = json.get("id").getAsString();
		
		return new MeetingDto(topic, agenda, start_time, duration, join_url, requester, null, meeting_id);
	}

	public String convertDateTimeBrasilToUTC(String brazilTimeIso){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(brazilTimeIso, formatter);
		LocalDateTime newDateTime = dateTime.plusHours(3);
		return newDateTime.format(formatter);
	}

	public String getClientId() {
		return client_id;
	}

	public String getClientSecret() {
		return client_secret;
	}
}

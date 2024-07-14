package com.example.demo.meeting.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.meeting.domain.ZoomMeetingDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MeetingServices {
	@Value("${zoom.client.id}")
	private String client_id;

	@Value("${zoom.client.secret}")
	private String client_secret;

	@Value("${redirect.uri}")
	private String redirect_uri;

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
				return response.body();

			} catch (Exception e) {
				return e.getMessage();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		String formData = data.entrySet().stream()
				.map(entry -> URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8) + "="
						+ URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));

		return BodyPublishers.ofString(formData);
	}

	public String getClientId() {
		return client_id;
	}

	public String getClientSecret() {
		return client_secret;
	}
}

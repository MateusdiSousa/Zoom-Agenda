package com.example.demo.meeting.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MeetingRepository extends JpaRepository<Meeting, String>{
	@Query("SELECT m FROM Meeting m WHERE m.meeting_id = :meetingId")
    Optional<Meeting> getByMeetingId(@Param("meetingId") String meetingId);
}

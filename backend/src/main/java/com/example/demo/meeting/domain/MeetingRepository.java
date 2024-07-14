package com.example.demo.meeting.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, String>{}

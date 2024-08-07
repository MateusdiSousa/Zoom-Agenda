package com.example.demo.attachment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, String>{
    public void deleteByUrl(String url);
}

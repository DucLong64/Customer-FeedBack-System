package com.longld.feedback_system.DTO.response;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class CommentResponse {
    private Long id;
    private String content;
    private Timestamp created_at;
}

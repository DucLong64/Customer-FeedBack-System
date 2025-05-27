package com.longld.feedback_system.DTO.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long feedbackId;
    private String content;

}

package com.longld.feedback_system.Service;

import com.longld.feedback_system.DTO.request.CommentRequest;
import com.longld.feedback_system.DTO.response.CommentResponse;

import java.util.List;

public interface InternalCommentService {
    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getCommentsForFeedback(Long feedbackId);
}

package com.longld.feedback_system.Service;

import com.longld.feedback_system.Converter.CommentRevert;
import com.longld.feedback_system.DTO.request.CommentRequest;
import com.longld.feedback_system.DTO.response.CommentResponse;
import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.InternalComment;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.FeedBackRepository;
import com.longld.feedback_system.Repository.InternalCommentRepository;
import com.longld.feedback_system.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InternalCommentServiceImpl implements InternalCommentService {
    private final UserRepository userRepository;
    private final FeedBackRepository feedBackRepository;
    private final InternalCommentRepository internalCommentRepository;
    private final CommentRevert commentRevert;
    @Override
    public CommentResponse addComment(CommentRequest commentRequest){
        FeedBack feedBack = feedBackRepository.findById(commentRequest.getFeedbackId())
                .orElseThrow(() -> new RuntimeException("FeedBack not found"));
        User admin = userRepository.findByUsername("admin")
                .orElseThrow(() -> new RuntimeException("User not found"));
        InternalComment comment = new InternalComment();
        comment.setFeedBack(feedBack);
        comment.setAdmin(admin);
        comment.setContent(commentRequest.getContent());
        comment.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        internalCommentRepository.save(comment);
        return commentRevert.revert(comment);
    };
    @Override
    public List<CommentResponse> getCommentsForFeedback(Long feedbackId) {
        return internalCommentRepository.findByFeedBackId(feedbackId)
                .stream()
                .map(commentRevert::revert)
                .collect(Collectors.toList());
    }
}

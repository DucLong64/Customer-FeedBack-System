package com.longld.feedback_system.Controller;

import com.longld.feedback_system.DTO.request.CommentRequest;
import com.longld.feedback_system.DTO.response.CommentResponse;
import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.FeedBackRepository;
import com.longld.feedback_system.Repository.UserRepository;
import com.longld.feedback_system.Service.FeedBackService;
import com.longld.feedback_system.Service.InternalCommentService;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private InternalCommentService internalCommentService;

    @GetMapping("/feedbacks/all")
    public ResponseEntity<?> getAllFeedBacks(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (required = false) String keyword,
            @RequestParam (required = false) FeedbackStatus status,
            @RequestParam (required = false) Long userId,
            @RequestParam (required = false) FeedbackType type
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedBack> Feedbacks = feedBackService.getAllFeedBacks(keyword, status, userId, type, pageable);
        return ResponseEntity.ok(Feedbacks);
    }

    @PutMapping("feedbacks/approve/{feedbackId}")
    public ResponseEntity<?> updateFeedBackStatus(
            @PathVariable Long feedbackId,
            @RequestParam FeedbackStatus status
    ){
        feedBackService.updateFeedBackStatus(feedbackId, status);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> addComment( @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok().body(internalCommentService.addComment(commentRequest));
    }
    @GetMapping("/comments/feedback/{feedbackId}")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long feedbackId){
        return ResponseEntity.ok(internalCommentService.getCommentsForFeedback(feedbackId));
    }

}

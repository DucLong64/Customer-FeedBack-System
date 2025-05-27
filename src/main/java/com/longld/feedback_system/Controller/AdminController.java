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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final FeedBackService feedBackService;
    private final InternalCommentService internalCommentService;
    @GetMapping("/dashboard")
    public String getDashboard(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (required = false) String keyword,
            @RequestParam (required = false) FeedbackStatus status,
            @RequestParam (required = false) Long userId,
            @RequestParam (required = false) FeedbackType type,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedBack> Feedbacks = feedBackService.getAllFeedBacks(keyword, status, userId, type, pageable);

        model.addAttribute("feedbacks", Feedbacks);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("userId", userId);
        model.addAttribute("type", type);

        // Truyền các enum cho dropdown trong Thymeleaf
        model.addAttribute("feedbackStatuses", FeedbackStatus.values());
        model.addAttribute("feedbackTypes", FeedbackType.values());
        return "dashboard";
    }

    @PostMapping("/feedbacks/approve/{feedbackId}")
    public String updateFeedBackStatus(
            @PathVariable Long feedbackId,
            @RequestParam FeedbackStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) FeedbackStatus currentStatus,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) FeedbackType type
    ){
        feedBackService.updateFeedBackStatus(feedbackId, status);
        // Chuyển hướng về dashboard, giữ lại tham số tìm kiếm và phân trang
        return "redirect:/admin/dashboard?page=" + page + "&size=" + size +
                (keyword != null ? "&keyword=" + keyword : "") +
                (currentStatus != null ? "&status=" + currentStatus : "") +
                (userId != null ? "&userId=" + userId : "") +
                (type != null ? "&type=" + type : "");
    }
    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok().body(internalCommentService.addComment(commentRequest));
    }
    @GetMapping("/comments/feedback/{feedbackId}")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long feedbackId){
        return ResponseEntity.ok(internalCommentService.getCommentsForFeedback(feedbackId));
    }

}

package com.longld.feedback_system.Controller;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Service.FeedBackService;
import com.longld.feedback_system.Service.UserService;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/feedbacks")

public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserService userService;
    @PreAuthorize("hasRole('USER')")
    // User tao feedbacks
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@RequestBody FeedBack feedBackRequest, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).get();
        feedBackRequest.setUser(user);
        FeedBack savedFeedBack = feedBackService.createFeedBack(feedBackRequest);
        return ResponseEntity.ok(savedFeedBack);
    }
    @PreAuthorize("hasRole('USER')")
    // User lay danh sach feedback
    @GetMapping("/view")
    public ResponseEntity<?> viewFeedback(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).get();
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedBack> Feedbacks = feedBackService.getFeedBacksByUser(user, pageable);
        return ResponseEntity.ok(Feedbacks);
    }

}

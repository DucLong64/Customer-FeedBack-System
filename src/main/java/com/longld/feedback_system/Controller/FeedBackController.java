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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/home")
@PreAuthorize("hasRole('USER')")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public String feedbackDashboard(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).get();

        Pageable pageable = PageRequest.of(page, size);
        Page<FeedBack> feedbackPage = feedBackService.getFeedBacksByUser(user, pageable);

        model.addAttribute("feedbackPage", feedbackPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", feedbackPage.getTotalPages());

        model.addAttribute("newFeedback", new FeedBack());
        model.addAttribute("feedbackTypes", FeedbackType.values());

        return "home";
    }

    @PostMapping()
    public String createFeedback(@ModelAttribute("newFeedback") FeedBack feedbackRequest,
                                 Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).get();
        feedbackRequest.setUser(user);
        feedBackService.createFeedBack(feedbackRequest);
        return "redirect:/home";
    }

}

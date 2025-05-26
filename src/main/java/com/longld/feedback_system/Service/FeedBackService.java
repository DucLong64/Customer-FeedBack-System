package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedBackService {
    // Customer tạo feedback
    FeedBack createFeedBack(FeedBack feedBack);
    // Lấy feedback của user ( Customer tự lấy của mình)
    Page<FeedBack> getFeedBacksByUser(User user, Pageable pageable);
    // Admin lấy all feedback
    Page<FeedBack> getAllFeedBacks(Pageable pageable);
    // Admin gắn trạng thái feedback
    void updateFeedBackStatus(Long feedBackId, FeedbackStatus feedBackStatus);

    Page <FeedBack> getAllFeedBacks (String keyword, FeedbackStatus status, Long userId, FeedbackType type, Pageable pageable);
}

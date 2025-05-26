package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.FeedBackRepository;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Override
    public FeedBack createFeedBack(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    @Override
    public Page<FeedBack> getFeedBacksByUser(User user, Pageable pageable) {
        return feedBackRepository.findByUser(user, pageable);
    }

    @Override
    public Page<FeedBack> getAllFeedBacks(Pageable pageable) {
        return feedBackRepository.findAll(pageable);
    }

    @Override
    public void updateFeedBackStatus(Long feedBackId, FeedbackStatus feedBackStatus) {
        Optional<FeedBack> optFeedBack = feedBackRepository.findById(feedBackId);
        if(optFeedBack.isPresent()) {
            FeedBack feedBack = optFeedBack.get();
            feedBack.setFeedbackStatus(feedBackStatus);
            feedBackRepository.save(feedBack);
        } else throw new RuntimeException("Feedback not found !");
    }

    @Override
    public Page <FeedBack> getAllFeedBacks (String keyword, FeedbackStatus status, Long userId, FeedbackType type, Pageable pageable){
        return feedBackRepository.findFeedBacks(keyword, status, userId, type, pageable);
    };
}

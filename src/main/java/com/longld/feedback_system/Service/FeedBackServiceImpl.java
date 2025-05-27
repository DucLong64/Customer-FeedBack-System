package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.FeedBackRepository;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private FeedBackRepository feedBackRepository;

    // Tạo mới phản hồi -> xóa cache để dữ liệu mới được cập nhật
    @Override
    @CacheEvict(value = {"feedbacksByUser", "allFeedbacks"}, allEntries = true)
    public FeedBack createFeedBack(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    // Lấy phản hồi của user với cache, key dựa trên userId và phân trang
    @Override
    @Cacheable(value = "feedbacksByUser", key = "#user.id + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FeedBack> getFeedBacksByUser(User user, Pageable pageable) {
        return feedBackRepository.findByUser(user, pageable);
    }

    // Lấy tất cả phản hồi với cache, key dựa trên phân trang
    @Override
    @Cacheable(value = "allFeedbacks", key = "'page:' + #pageable.pageNumber + '-size:' + #pageable.pageSize")
    public Page<FeedBack> getAllFeedBacks(Pageable pageable) {
        return feedBackRepository.findAll(pageable);
    }

    // Cập nhật trạng thái phản hồi -> xóa cache để dữ liệu mới được cập nhật
    @Override
    @CacheEvict(value = {"feedbacksByUser", "allFeedbacks"}, allEntries = true)
    public void updateFeedBackStatus(Long feedBackId, FeedbackStatus feedBackStatus) {
        Optional<FeedBack> optFeedBack = feedBackRepository.findById(feedBackId);
        if(optFeedBack.isPresent()) {
            FeedBack feedBack = optFeedBack.get();
            feedBack.setFeedbackStatus(feedBackStatus);
            feedBackRepository.save(feedBack);
        } else throw new RuntimeException("Feedback not found !");
    }

    // Lấy phản hồi theo filter keyword, trạng thái, userId, loại với cache
    @Override
    @Cacheable(value = "filteredFeedbacks", key = "T(java.util.Objects).hash(#keyword, #status, #userId, #type, #pageable.pageNumber, #pageable.pageSize)")
    public Page<FeedBack> getAllFeedBacks(String keyword, FeedbackStatus status, Long userId, FeedbackType type, Pageable pageable){
        return feedBackRepository.findFeedBacks(keyword, status, userId, type, pageable);
    }
}

package com.longld.feedback_system.Repository;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    // Lấy feedback theo user, phân trang
    Page<FeedBack> findByUser(User user, Pageable pageable);

    // Lọc phản hồi theo trạng thái
    Page<FeedBack> findByFeedbackStatus(FeedbackStatus feedbackStatus, Pageable pageable);

    // Lọc theo loại
    Page<FeedBack> findByFeedbackType(FeedbackType feedbackType, Pageable pageable);

    Page<FeedBack> findAll(Pageable pageable);

    @Query ("SELECT f from FeedBack f "+
            "WHERE (:keyword is null or lower(f.content) like lower(concat('%', :keyword, '%'))) "+
            "AND (:status is null or f.feedbackStatus = :status) "+
            "AND (:userId is null or f.user.id = :userId) "+
            "AND (:type is null or f.feedbackType = :type)"
    )
    Page<FeedBack> findFeedBacks(
            @Param("keyword") String keyword,
            @Param("status") FeedbackStatus status,
            @Param("userId") Long userId,
            @Param("type") FeedbackType type,
            Pageable pageable
    );

}

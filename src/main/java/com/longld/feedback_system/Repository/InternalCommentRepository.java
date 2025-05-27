package com.longld.feedback_system.Repository;

import com.longld.feedback_system.Entity.InternalComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalCommentRepository extends JpaRepository<InternalComment, Long> {
    List<InternalComment> findByFeedBackId(Long feedBackId);

}

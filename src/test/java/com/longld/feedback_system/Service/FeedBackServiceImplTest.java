package com.longld.feedback_system.Service;

import com.longld.feedback_system.Entity.FeedBack;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.FeedBackRepository;
import com.longld.feedback_system.Util.FeedbackType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedBackServiceImplTest {
    @Mock
    private FeedBackRepository feedBackRepository;
    @InjectMocks
    private FeedBackServiceImpl feedBackService;

    private FeedBack feedBack;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        feedBack = new FeedBack();
        feedBack.setId(1L);
        feedBack.setContent(" Đánh giá quần short");
        feedBack.setFeedbackType(FeedbackType.Require);
        feedBack.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        feedBack.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        feedBack.setRating(4);
        feedBack.setUser(new User());
    }

    @Test
    void testCreateFeedBack() {
        when(feedBackRepository.save(any(FeedBack.class))).thenReturn(feedBack);

        FeedBack createdFeedBack = feedBackService.createFeedBack(feedBack);

        assertNotNull (createdFeedBack);
        assertEquals(" Đánh giá quần short", createdFeedBack.getContent());
        verify(feedBackRepository, times(1)).save(feedBack);
    }

}

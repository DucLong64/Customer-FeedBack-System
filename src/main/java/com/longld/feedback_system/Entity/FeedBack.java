package com.longld.feedback_system.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.longld.feedback_system.Util.FeedbackStatus;
import com.longld.feedback_system.Util.FeedbackType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Data
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;
    @Enumerated(EnumType.STRING)
    private FeedbackStatus feedbackStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "feedBack")
    private List<InternalComment> internalComments ;

    private Timestamp created_at;
    private Timestamp updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
        feedbackStatus = FeedbackStatus.PENDING;
    }
    @PreUpdate
    protected void onUpdate() {
        updated_at = new Timestamp(System.currentTimeMillis());
    }
}

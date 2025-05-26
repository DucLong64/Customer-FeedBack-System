package com.longld.feedback_system.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class InternalComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feedback_id", nullable = false)
    private FeedBack feedBack;

    @ManyToOne
    @JoinColumn(name="admin_id", nullable = false)
    private User admin;

    private Timestamp created_at;
}

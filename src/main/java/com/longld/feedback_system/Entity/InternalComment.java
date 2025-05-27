package com.longld.feedback_system.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feedback_id", nullable = false)
    private FeedBack feedBack;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="admin_id", nullable = false)
    private User admin;

    private Timestamp created_at;
}

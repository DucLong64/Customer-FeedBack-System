package com.longld.feedback_system.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Timestamp created_at;
    private Timestamp updated_at;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles= new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedBack> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InternalComment> internalComments = new ArrayList<>();
}

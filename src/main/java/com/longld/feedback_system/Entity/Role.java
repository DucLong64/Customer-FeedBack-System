package com.longld.feedback_system.Entity;

import com.longld.feedback_system.Util.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}

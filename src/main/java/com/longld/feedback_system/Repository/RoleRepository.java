package com.longld.feedback_system.Repository;

import com.longld.feedback_system.Entity.Role;
import com.longld.feedback_system.Util.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (RoleEnum name);
}

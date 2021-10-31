package com.bootcamp.Entities.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
    @Id
    @Column(name = "USER_ID")
    UUID userId;
    @Column(name = "ROLE_ID")
    UUID roleId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}

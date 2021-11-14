package com.bootcamp.Entities.User;

import javax.persistence.*;


@Entity
@Table(name = "USER_ROLE")
public class UserRole {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name = "USER_ID")
    long userId;
    @Column(name = "ROLE_ID")
    long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}

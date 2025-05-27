package com.example.projetosd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UserSessions")
public class UserSession {

    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    public UserSession() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

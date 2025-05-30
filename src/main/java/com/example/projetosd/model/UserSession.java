package com.example.projetosd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "userSessions")
public class UserSession {

    @Id
    @Column(name = "userId")
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

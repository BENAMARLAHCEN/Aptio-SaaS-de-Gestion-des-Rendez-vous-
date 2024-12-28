package com.youcode.aptio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {
    }

    public Token(Long id, String token, boolean expired, boolean revoked, User user) {
        this.id = id;
        this.token = token;
        this.expired = expired;
        this.revoked = revoked;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String token;
        private boolean expired;
        private boolean revoked;
        private User user;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public Builder revoked(boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Token build() {
            return new Token(id, token, expired, revoked, user);
        }
    }
}
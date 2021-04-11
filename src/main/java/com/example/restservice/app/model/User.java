package com.example.restservice.app.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private BigInteger id;

    @Column(unique = true)
    private String email;

    private boolean isVerified = false;

    @OneToMany(mappedBy = "user")
    private Set<Subscription> subscriptions;

    public User(String email) {
        this.email = email;
    }

    public User(BigInteger id, String email, boolean isVerified) {
        this.id = id;
        this.email = email;
        this.isVerified = isVerified;
    }

    protected User() {
        this("admin@admin.com");
    }

    public BigInteger getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return isVerified;
    }


    public void toggleVerified() {
        this.isVerified = !this.isVerified;
    }

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}

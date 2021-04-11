package com.example.restservice.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id, user_id"}))
public final class Subscription {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Item item;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    public Subscription(Item item, User user) {
        this.item = item;
        this.user = user;
        this.verificationCode = UUID.randomUUID().toString();
    }

    protected Subscription() {
    }

    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public Item getItem() {
        return item;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "item=" + item +
                ", user=" + user +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                '}';
    }
}

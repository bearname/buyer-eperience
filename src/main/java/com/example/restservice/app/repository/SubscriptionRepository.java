package com.example.restservice.app.repository;

import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    @Query("select count(sub.id) from Subscription sub where sub.item.id = ?1 AND sub.user.id = ?2")
    long getCountByItemIdAndUserId(String itemId, BigInteger userId);

    @Query(value = "SELECT * FROM subscription WHERE item_id = ?1 AND user_id = ?2", nativeQuery = true)
    Subscription findByItemIdAndUserId(String itemId, BigInteger userId);

    @Query("select distinct count(sub.item) from Subscription sub where sub.isActive = true and sub.isVerified = true")
    BigInteger getNeededCheckCount();

    @Query("select distinct(sub.item) from Subscription sub where sub.isActive = true and sub.isVerified = true")
    List<Item> getNeededCheck(Pageable pageable);

    Subscription findByItemIdAndUserEmail(String itemId, String email);

    @Query("select sub from Subscription sub where sub.isActive = true and sub.user.id = ?1")
    Page<Subscription> following(final BigInteger userId, final Pageable pageable);

    @Query(value = "SELECT users.id, users.email, users.is_verified FROM subscription LEFT JOIN users ON subscription.user_id = users.id WHERE item_id = ?1", nativeQuery = true)
    List<Object[]> getSubscribers(String itemId);
}

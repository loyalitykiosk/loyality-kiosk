package com.kiosk.repository;

import com.kiosk.domain.Subscription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscription entity.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

}

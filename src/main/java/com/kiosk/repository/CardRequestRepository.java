package com.kiosk.repository;

import com.kiosk.domain.CardRequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CardRequest entity.
 */
public interface CardRequestRepository extends JpaRepository<CardRequest,Long> {

    @Query("select request from CardRequest request where request.kiosk.customer.login = ?#{principal.username}")
    List<CardRequest> findRequestsFromCurrentUserKiosks();

}

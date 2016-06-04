package com.kiosk.repository;

import com.kiosk.domain.Kiosk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Kiosk entity.
 */
public interface KioskRepository extends JpaRepository<Kiosk,Long> {

    @Query("select kiosk from Kiosk kiosk where kiosk.customer.login = ?#{principal.username}")
    Page<Kiosk> findByCustomerIsCurrentUser(Pageable pageable);

}

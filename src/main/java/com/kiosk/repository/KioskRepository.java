package com.kiosk.repository;

import com.kiosk.domain.Kiosk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Kiosk entity.
 */
public interface KioskRepository extends JpaRepository<Kiosk,Long> {

    @Query("select kiosk from Kiosk kiosk where kiosk.customer.login = ?#{principal.username}")
    Page<Kiosk> findByCustomerIsCurrentUser(Pageable pageable);

    @Query("select kiosk from Kiosk kiosk where kiosk.license = :license and kiosk.customer.login = ?#{principal.username}")
    Kiosk findByLicense(@Param("license") String license);
}

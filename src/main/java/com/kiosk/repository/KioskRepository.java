package com.kiosk.repository;

import com.kiosk.domain.Kiosk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Kiosk entity.
 */
public interface KioskRepository extends JpaRepository<Kiosk,Long> {

    @Query("select kiosk from Kiosk kiosk where kiosk.customer.login = ?#{principal.username}")
    Page<Kiosk> findByCustomerIsCurrentUser(Pageable pageable);

    @Query("select kiosk from Kiosk kiosk where kiosk.license = :license and kiosk.customer.login = ?#{principal.username}")
    Kiosk findByLicenseAndCurrentUser(@Param("license") String license);

    @Query("select kiosk from Kiosk kiosk where kiosk.license = :license")
    Kiosk findByLicense(@Param("license") String license);

    @Query("select kiosk from Kiosk kiosk where kiosk.id = :id and kiosk.customer.login = ?#{principal.username}")
    Kiosk findOneOfCurrentUser(@Param("id")Long id);

    @Query("delete from Kiosk kiosk where kiosk.customer.id = :userId")
    @Modifying
    void deleteUserKiosks(@Param("userId") Long userId);

    @Query("select kiosk from Kiosk kiosk where kiosk.customer.id = :userId")
    List<Kiosk> findByUser(@Param("userId") Long userId);
}

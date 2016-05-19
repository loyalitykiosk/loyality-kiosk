package com.kiosk.repository;

import com.kiosk.domain.Promotion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Promotion entity.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

    @Query("select promotion from Promotion promotion where promotion.user.login = ?#{principal.username}")
    List<Promotion> findByUserIsCurrentUser();

}

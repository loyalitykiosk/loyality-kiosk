package com.kiosk.repository;

import com.kiosk.domain.Promotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Promotion entity.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

    @Query("select promotion from Promotion promotion where promotion.user.login = ?#{principal.username}")
    Page<Promotion> findByUserIsCurrentUser(Pageable pageable);


    @Query("select promotion from Promotion promotion where current_date() between promotion.dateStart and promotion.dateEnd and promotion.user.login = ?#{principal.username}")
    List<Promotion> findByUserIsCurrentUser();

}

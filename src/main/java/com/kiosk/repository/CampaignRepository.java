package com.kiosk.repository;

import com.kiosk.domain.Campaign;

import com.kiosk.domain.enumeration.CampaignStatus;
import com.kiosk.domain.enumeration.CampaignType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Campaign entity.
 */
public interface CampaignRepository extends JpaRepository<Campaign,Long> {

    @Query("select campaign from Campaign campaign where campaign.user.login = ?#{principal.username}")
    List<Campaign> findByUserIsCurrentUser();

    @Query("select campaign from Campaign campaign where campaign.id = :id and campaign.user.login = ?#{principal.username}")
    Campaign findByIdAndUserIsCurrentUser(@Param("id") Long id);

    @Query("select campaign from Campaign campaign where campaign.type=:type and campaign.status=:status")
    List<Campaign> findByCampaignTypeAndStatus(@Param("type") CampaignType type,@Param("status")CampaignStatus status);
}

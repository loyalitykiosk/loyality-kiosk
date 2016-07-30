package com.kiosk.repository;

import com.kiosk.domain.UserSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserSettings entity.
 */
public interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {

}

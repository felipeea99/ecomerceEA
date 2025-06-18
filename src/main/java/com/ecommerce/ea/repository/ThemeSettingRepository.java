package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.ThemeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ThemeSettingRepository extends JpaRepository<ThemeSetting, Integer> {
    @Query("SELECT ts FROM ThemeSetting ts WHERE ts.store = :store AND ts.isActive = true")
    ThemeSetting findThemeActive(@Param("store") UUID store);
}

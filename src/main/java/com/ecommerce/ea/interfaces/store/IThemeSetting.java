package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.entities.store.ThemeSetting;

import java.util.List;
import java.util.UUID;

public interface IThemeSetting{

    ThemeSetting addTheme(ThemeSetting themeObject);
    ThemeSetting editTheme(ThemeSetting themeObject);
    Boolean deleteTheme(int themeId);
    Boolean setThemeToShow(int themeId, UUID storeId);
    List<ThemeSetting> getAllThemes();
    ThemeSetting getThemeSettingActive(UUID storeId);
    ThemeSetting getThemeById(int themeId);
    List<ThemeSetting> findAllThemesByStoreId(UUID storeId);

}
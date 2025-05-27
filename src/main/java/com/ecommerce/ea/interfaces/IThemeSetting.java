package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.ThemeSetting;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IThemeSetting{

    CompletableFuture<Boolean> AddTheme(ThemeSetting themeObject);
    CompletableFuture<Boolean> EditTheme(ThemeSetting themeObject);
    CompletableFuture<Boolean> DeleteTheme(int themeId);
    CompletableFuture<Boolean> SetThemeToShow(int themeId);
    CompletableFuture<List<ThemeSetting>> GetAllThemes();
    CompletableFuture<ThemeSetting> GetThemeSettingActive();
    CompletableFuture<ThemeSetting> GetThemeById(int themeId);
}
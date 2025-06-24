package com.ecommerce.ea.controllers.store;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.entities.store.ThemeSetting;
import com.ecommerce.ea.services.auth.StoreService;
import com.ecommerce.ea.services.store.ThemeSettingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/themes")
public class ThemeSettingController {
    private final ThemeSettingService themeSettingService;
    private final StoreService storeService;

    public ThemeSettingController(ThemeSettingService themeSettingService, StoreService storeService) {
        this.themeSettingService = themeSettingService;
        this.storeService = storeService;
    }

    @ValidateStoreAccess
    @GetMapping("/{storeName}")
    public ThemeSetting addTheme(@RequestBody ThemeSetting themeSetting) {
        UUID storeId = StoreContextHolder.getStoreId();
        themeSetting.setStore(storeService.findStoreByIdBaseForm(storeId));
        return themeSettingService.addTheme(themeSetting);
    }

    @ValidateStoreAccess
    @PutMapping("/{storeName}")
    public ThemeSetting editTheme(@RequestBody ThemeSetting themeSetting) {
        return themeSettingService.editTheme(themeSetting);
    }

    @ValidateStoreAccess
    @GetMapping("/{storeName}/retrieveAllThemes")
    public List<ThemeSetting> getAllThemes() {
        return themeSettingService.getAllThemes();
    }
    @ValidateStoreAccess
    @GetMapping("/{storeName}/{themeId}/setThemeToShow")
    public Boolean setThemeToShow(@PathVariable int themeId) {
        UUID storeId = StoreContextHolder.getStoreId();
        return themeSettingService.setThemeToShow(themeId,storeId);
    }
    @ValidateStoreAccess
    @GetMapping("/{storeName}/{themeId}")
    public Boolean getThemeSettingActive(@PathVariable int themeId) {
        return themeSettingService.deleteTheme(themeId);
    }

    @ValidateStoreAccess
    @GetMapping("/{storeName}/themesList")
    public List<ThemeSetting> findAllThemesByStoreId() {
        UUID storeId = StoreContextHolder.getStoreId();
        return themeSettingService.findAllThemesByStoreId(storeId);
    }



}

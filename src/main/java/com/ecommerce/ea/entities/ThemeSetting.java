package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ThemeSetting {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer themeId;

        @Size(min = 7)
        private String primaryColor;

        @Size(min = 7)
        private String secondaryColor;

        @Size(min = 7)
        private String primaryTextColor;

        @Size(min = 7)
        private String secondaryTextColor;

        @Size(min = 7)
        private String backgroundColor;

        @Size(min = 7)
        private String primaryHoverColor;

        @Size(min = 7)
        private String secondaryHoverColor;

        @Size(min = 7)
        private String backgroundTextColor;

        @Size(min = 7)
        private String cardBackground;

        @Size(min = 7)
        private String cardText;

        @Size(min = 7)
        private String freeDeliveryText;

        private boolean isActive;
        @ManyToOne
        @JoinColumn(name = "storeId")
        private Store store;
}

package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class productSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productSizeId;
    @NotBlank
    private double price;
    @NotBlank
    private boolean isActive;
    @NotNull
    private size size;
    @NotNull
    private productMultiPrice productMultiPrice;
}

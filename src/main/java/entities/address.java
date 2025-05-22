package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
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
public class address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @NotBlank(message = "country is mandatory")
    @Min(5)
    private String country;
    @NotBlank(message = "street is mandatory")
    private String street;
    @NotBlank(message = "number is mandatory")
    private String number;
    @NotBlank(message = "colony is mandatory")
    private String colony;
    @NotBlank(message = "town is mandatory")
    private String town;
}

package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class paymentStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storePaidId;
    @NotBlank
    private Date date;
    @NotBlank
    private store store;
    @NotBlank
    @Min(0)
    private double amount;
    @NotBlank
    private String referencePayment;

}

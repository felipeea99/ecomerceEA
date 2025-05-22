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

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class shoppingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    @NotBlank
    private Date dateTime;
    @NotBlank
    private int quantity;
    @NotBlank
    private String status;
    @NotBlank
    private String purchaseUUID;
    @NotNull
    private product product;
    @NotNull
    private customer customer;
    @NotBlank
    private String paymentProviderId;
}

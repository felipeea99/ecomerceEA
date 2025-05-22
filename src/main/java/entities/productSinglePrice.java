package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class productSinglePrice extends product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productSinglePriceId;
    private double price;

    public productSinglePrice(int productSinglePriceId, double price) {
        super();
        this.productSinglePriceId = productSinglePriceId;
        this.price = price;
    }
}

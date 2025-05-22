package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class shoppingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    private Date dateTime;
    private int quantity;
    private product product;
    private customer customer;

    public shoppingHistory() {

    }

    public shoppingHistory(customer customer, product product, Date dateTime, int historyId, int quantity) {
        this.customer = customer;
        this.product = product;
        this.dateTime = dateTime;
        this.historyId = historyId;
        this.quantity = quantity;
    }
}

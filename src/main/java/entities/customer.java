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
public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;
    private user user;

}

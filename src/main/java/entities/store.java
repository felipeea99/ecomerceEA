package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@Entity
public class store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeID;
    private String storeName;
    private String storeEmail;
    private User user;

    public store() {
    }
}

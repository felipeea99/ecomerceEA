package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String storeName;
    @NotBlank
    @Email
    private String storeEmail;
    @NotNull
    private User user;

    public store() {
    }
}

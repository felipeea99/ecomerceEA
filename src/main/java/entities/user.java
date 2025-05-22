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
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String userName;
    private String userLastName1;
    private String userLastName2;
    private String password;
    private String email;
    private String phoneNumber;

    public user() {
    }

    public user(String userId, String email, String phoneNumber, String password, String userLastName2, String userLastName1, String userName) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userLastName2 = userLastName2;
        this.userLastName1 = userLastName1;
        this.userName = userName;
    }
}

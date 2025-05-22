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
public class photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoID;
    private String photoValue;
    private product product;


}

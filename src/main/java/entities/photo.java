package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoID;
    @NotBlank
    private String photoValue;
    @NotNull
    private product product;


}

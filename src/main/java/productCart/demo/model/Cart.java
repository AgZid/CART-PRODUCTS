package productCart.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import productCart.demo.enumerator.CardStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateOfOrder;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products;
}

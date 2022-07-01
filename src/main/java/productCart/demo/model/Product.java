package productCart.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer amount;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id")
    @JsonBackReference
    private Cart cart;
}

package productCart.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productCart.demo.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String nme);

    List<Product> findByPriceBetween(Double start, Double end);

    //    @Query("SELECT coalesce(max(pr.price), 0) FROM Product pr")
    @Query("SELECT pr FROM Product pr where pr.price = (select max(prr.price) from Product prr)")
    Product findMaxPrice();
}

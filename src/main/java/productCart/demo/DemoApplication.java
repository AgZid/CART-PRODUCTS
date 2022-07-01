package productCart.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import productCart.demo.enumerator.CardStatus;
import productCart.demo.model.Cart;
import productCart.demo.model.Product;
import productCart.demo.repository.CartRepository;
import productCart.demo.repository.ProductRepository;

import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean //aplikacijos paleidimo metu graznamas instansas bus idedamas i Spring konteineri
    public CommandLineRunner constructCommandLineRunnerBean(final CartRepository cartRepository,
                                                            final ProductRepository productRepository) {


        return args -> {
            Cart cart1 = Cart.builder()
                    .dateOfOrder(LocalDate.of(2022, 06, 01))
                    .status(CardStatus.ORDERED)
                    .build();

            cartRepository.save(cart1);

            Product milk = Product.builder()
                    .name("Milk")
                    .amount(3)
                    .price(1.2)
                    .cart(cart1)
                    .build();

            productRepository.save(milk);
        };
    }

}

package productCart.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productCart.demo.exception.CartNotFoundException;
import productCart.demo.model.Cart;
import productCart.demo.service.CartService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> findAll() {
        return cartService.findAll();
    }

    @PostMapping
    public ResponseEntity<List<Cart>> saveCart(@RequestBody Cart cart) {
        return cartService.save(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Cart>> updateCart(@PathVariable("id") int id, @RequestBody Cart cart) throws CartNotFoundException {
        return cartService.update(id, cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Cart>> removeById(@PathVariable("id") int id) throws MessagingException, IOException, CartNotFoundException {
        return cartService.remove(id);
    }

}

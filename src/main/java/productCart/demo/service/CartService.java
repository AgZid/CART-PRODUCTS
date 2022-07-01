package productCart.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import productCart.demo.exception.CartNotFoundException;
import productCart.demo.model.Cart;
import productCart.demo.repository.CartRepository;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final EmailService emailService;
    private final JsonService jsonService;

    public CartService(CartRepository cartRepository, EmailService emailService, JsonService jsonService) {
        this.cartRepository = cartRepository;
        this.emailService = emailService;
        this.jsonService = jsonService;
    }

    public ResponseEntity<List<Cart>> findAll() {
        LOGGER.info("Finding all carts.");
        return new ResponseEntity(cartRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Cart>> save(Cart newCart) {
        LOGGER.info("Saving new cart " + newCart);
        cartRepository.save(newCart);
        LOGGER.info("New cart was saved");
        return new ResponseEntity(cartRepository.findAll(), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Cart>> update(Integer id, Cart cartToUpdate) throws CartNotFoundException {
        Optional<Cart> foundCart = cartRepository.findById(id);
        if (foundCart.isPresent()) {
            LOGGER.info("Updating cart " + cartToUpdate);
            foundCart.map(
                    cart -> {
                        cart.setStatus(cartToUpdate.getStatus());
                        cart.setDateOfOrder(cartToUpdate.getDateOfOrder());
                        return cartRepository.save(cart);
                    }
            );
            LOGGER.info("Cart updated");
            return new ResponseEntity(cartRepository.findAll(), HttpStatus.OK);
        } else {
            throw new CartNotFoundException("Cart not found with id " + id);
        }
    }

    public ResponseEntity<List<Cart>> remove(Integer id) throws MessagingException, IOException, CartNotFoundException {
        Optional<Cart> foundCart = cartRepository.findById(id);
        if (foundCart.isPresent()) {
            LOGGER.info("Creating Json file");
            jsonService.exportToJson(foundCart);
            LOGGER.info("Removing cart id" + id);
            cartRepository.deleteById(id);
            LOGGER.info("Cart was removed");
            LOGGER.info("Sending email");
            emailService.sendEmail(String.format("Cart id %d was removed", id));
            LOGGER.info("Email was sent");
            return new ResponseEntity(cartRepository.findAll(), HttpStatus.OK);
        } else {
            throw new CartNotFoundException("Cart not found with id " + id);
        }
    }
}
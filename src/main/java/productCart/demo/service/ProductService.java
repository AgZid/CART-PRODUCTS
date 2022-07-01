package productCart.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import productCart.demo.exception.CartNotFoundException;
import productCart.demo.exception.ProductNotFoundException;
import productCart.demo.model.Cart;
import productCart.demo.model.Product;
import productCart.demo.repository.CartRepository;
import productCart.demo.repository.ProductRepository;
import productCart.demo.validation.ProductRequestValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ProductRequestValidationService productRequestValidationService;
    private final CartService cartService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, ProductRequestValidationService productRequestValidationService, CartService cartService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.productRequestValidationService = productRequestValidationService;
        this.cartService = cartService;
    }

    public ResponseEntity<List<Product>> findAll() {
        LOGGER.info("Finding all products.");
        return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> addProduct(Product product) throws ProductRequestValidationException {
        LOGGER.info("Adding new product - " + product.getName());
        productRequestValidationService.validateRequest(product);
        productRepository.save(product);
        return new ResponseEntity(productRepository.findAll(), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Product>> updateProduct(Integer id, Product productToUpdate) throws ProductNotFoundException {
        if (isProductPresent(id)) {
            LOGGER.info("Updating product ID " + id + " to " + productToUpdate.getName());
            productRepository.findById(id).map(
                    product -> {
                        product.setName(productToUpdate.getName());
                        product.setPrice(productToUpdate.getPrice());
                        product.setAmount(productToUpdate.getAmount());
                        try {
                            productRequestValidationService.validateRequest(product);
                        } catch (ProductRequestValidationException e) {
                            e.printStackTrace();
                        }
                        return productRepository.save(product);
                    }
            );
            return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<List<Product>> assignProductToCart(int productId, int cartId) throws ProductNotFoundException, CartNotFoundException {
        LOGGER.info("Assigning product " + productId + " to cart " + cartId);
        if (isProductPresent(productId) && cartService.isCartPresent(cartId)) {
            Product product = productRepository.findById(productId).get();
            Cart cart = cartRepository.findById(cartId).get();
            product.setCart(cart);
            productRepository.save(product);
            LOGGER.info("Product assigned to cart.");
            return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<List<Product>> removeById(int id) throws ProductNotFoundException {
        if (isProductPresent(id)) {
            LOGGER.info("Removing product id " + id);
            productRepository.deleteById(id);
            LOGGER.info("Product removed");
            return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<List<Product>> findByName(String name) {
        LOGGER.info("Finding product by name " + name);
        return new ResponseEntity(productRepository.findByName(name), HttpStatus.FOUND);
    }

    public ResponseEntity<List<Product>> findByPriceRange(Double minPrice, Double maxPrice) {
        LOGGER.info("Finding product by price range" + minPrice + maxPrice);
        return new ResponseEntity(productRepository.findByPriceBetween(minPrice, maxPrice), HttpStatus.FOUND);
    }

    public ResponseEntity<Product> findMaxPrice() {
        LOGGER.info("Finding product by max price");
        return new ResponseEntity(productRepository.findMaxPrice(), HttpStatus.FOUND);
    }

    private boolean isProductPresent(int id) throws ProductNotFoundException {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (!foundProduct.isPresent()) {
            throw new ProductNotFoundException("Product not found with id " + id);
        } else return true;
    }

}

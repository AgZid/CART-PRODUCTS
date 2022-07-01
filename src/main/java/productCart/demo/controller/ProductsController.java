package productCart.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productCart.demo.exception.CartNotFoundException;
import productCart.demo.exception.ProductNotFoundException;
import productCart.demo.model.Product;
import productCart.demo.service.ProductService;
import productCart.demo.validation.ProductRequestValidationException;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return productService.findAll();
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> findByPriceRange(@RequestParam Double minPrice,
                                                          @RequestParam Double maxPrice) {
        return productService.findByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/maxprice")
    public ResponseEntity<Product> findMaxPrice() {
        return productService.findMaxPrice();
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> findByName(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) throws ProductRequestValidationException {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Product>> update(@PathVariable("id") int id, @RequestBody Product product) throws ProductNotFoundException {
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{productId}/cart/{cartId}")
    public ResponseEntity<List<Product>> addProductToCart(@PathVariable("productId") int productId,
                                                          @PathVariable("cartId") int cartId) throws ProductNotFoundException, CartNotFoundException {
        return productService.assignProductToCart(productId, cartId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Product>> removeById(@PathVariable("id") int id) throws ProductNotFoundException {
        return productService.removeById(id);
    }
}

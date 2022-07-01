package productCart.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productCart.demo.model.Cart;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class JsonService {

    @Autowired
    private ObjectMapper objectMapper;

    public void exportToJson(Optional<Cart> cart) {

//        if (CollectionUtils) - prideti tikrinima, jei listas tuscias
        try {
            objectMapper.writeValue(new File("cart.json"), cart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.model.Product;
import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.ShoppingCartItem;
import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.DummyLoginRepository;
import co.g2academy.tokomurah.repository.ProductRepository;
import co.g2academy.tokomurah.repository.ShoppingCartRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import co.g2academy.tokomurah.service.CheckoutService;
import co.g2academy.tokomurah.view.AddToCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ifnub
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    @Autowired private ShoppingCartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CheckoutService checkoutService;
    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(@RequestBody AddToCart atc,
            Principal principal){
        User u = userRepository.findUserByUsername(principal.getName());
        if(u == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not login");
        }
        Optional<Product> opt = productRepository.findById(atc.getProductId());
        if(opt.isEmpty()){
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product p = opt.get();
        ShoppingCart cart = 
            cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        if(cart == null){
            cart = new ShoppingCart();
            cart.setUser(u);
            cart.setStatus("ACTIVE");
            List<ShoppingCartItem> items = new ArrayList<>();
            cart.setItems(items);
        }
        ShoppingCartItem item = null;
        for (ShoppingCartItem sci : cart.getItems()) {
            if(sci.getProduct().getId().equals(atc.getProductId())){
                item = sci;
                break;
            }
        }
        if(item == null){
            item = new ShoppingCartItem();
            item.setCart(cart);
            item.setProduct(p);
            item.setQuantity(atc.getQuantity());
            item.setPrice(p.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + atc.getQuantity());
        }
        cartRepository.save(cart);
        return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/cart")
    public ResponseEntity getCart(Principal principal) {
        User u = userRepository.findUserByUsername(
                principal.getName());
        ShoppingCart cart = 
            cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) throws JsonProcessingException {
        User u = userRepository.findUserByUsername(
                principal.getName());
        ShoppingCart cart = 
            cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        if(cart == null){
            return ResponseEntity.badRequest().body("Cart not found");
        }
        checkoutService.checkout(cart);
        return ResponseEntity.ok("OK");
    }
}

package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.model.Product;
import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.ProductRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ifnub
 */
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/product")
    public List<Product> getProducts() {
        return repository.findAll();
    }
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return repository.findById(id).get();
    }
    @PostMapping("/save/product")
    public String save(@RequestBody Product product, 
            Principal principal) {
        User u = userRepository.findUserByUsername(
                principal.getName());
        product.setUser(u);
        repository.save(product);
        return "success";
    }
    @PutMapping("/update/product/{id}")
    public ResponseEntity update(
            @RequestBody Product product,
            Principal principal) {
        Optional<Product> opt = 
                repository.findById(product.getId());
        if(!opt.isEmpty()){
            Product pFromDb = opt.get();
            if(pFromDb.getUser().getUsername()
                    .equals(principal.getName())){
                pFromDb.setDescription(
                        product.getDescription());
                pFromDb.setName(product.getName());
                pFromDb.setPrice(product.getPrice());
                pFromDb.setStock(product.getStock());
                repository.save(pFromDb);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.badRequest()
                .body("Product not found");
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id,
            Principal principal) {
        Optional<Product> opt = 
                repository.findById(id);
        if(!opt.isEmpty()){
            Product pFromDb = opt.get();
            if(pFromDb.getUser().getUsername()
                    .equals(principal.getName())){
                repository.deleteById(id);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.badRequest()
                .body("Product not found");
    }

}

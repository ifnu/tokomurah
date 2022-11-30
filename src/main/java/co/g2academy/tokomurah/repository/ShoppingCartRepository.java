package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dian
 */
@Repository
public interface ShoppingCartRepository 
        extends JpaRepository<ShoppingCart, Integer>{
    
    public ShoppingCart getShoppingCartByUserAndStatus(
        User user, String status);
}

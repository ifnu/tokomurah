
package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.Product;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dian
 */
@Repository
public interface ProductRepository 
        extends JpaRepository<Product, Integer>{

    @Override
    @Cacheable(value = "productById", key ="#id")
    public Optional<Product> findById(Integer id);
 
    
}

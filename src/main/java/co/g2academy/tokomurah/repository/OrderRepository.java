
package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnub
 */
@Repository
public interface OrderRepository 
        extends JpaRepository<Order, Integer>{

    
}

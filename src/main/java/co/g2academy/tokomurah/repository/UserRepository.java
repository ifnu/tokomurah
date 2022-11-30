package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dian
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    public User findUserByUsername(String username);
}

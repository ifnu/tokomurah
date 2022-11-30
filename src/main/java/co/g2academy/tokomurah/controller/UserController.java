
package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.DummyLoginRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import co.g2academy.tokomurah.validator.RegexEmailValidator;
import co.g2academy.tokomurah.validator.RegexPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dian
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RegexEmailValidator emailValidator;
    @Autowired
    private RegexPasswordValidator passwordValidator;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        User userFromDb = repository.findUserByUsername(user.getUsername());
        if(userFromDb == null
                && emailValidator.emailPatternMatch(user.getUsername())
                && passwordValidator.passwordPatternMatch(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        } else {
            return ResponseEntity.badRequest()
                    .body("User exist, email or password invalid");
        }
        return ResponseEntity.ok().body("OK");
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        User userFromDb = repository.findUserByUsername(user.getUsername());
        if(userFromDb != null
                && userFromDb.getPassword().equals(user.getPassword())){
                DummyLoginRepository.setLoggedInUser(userFromDb);
            return ResponseEntity.ok().body("Login Success");
        }
        return ResponseEntity.badRequest()
                    .body("Login Fail");
    }
}

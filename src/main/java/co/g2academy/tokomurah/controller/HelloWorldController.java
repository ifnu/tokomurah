
package co.g2academy.tokomurah.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ifnub
 */
@RestController
@RequestMapping("/api")
public class HelloWorldController {
    
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello this is my first spring boot application, yaaayy!";
    }
    
}

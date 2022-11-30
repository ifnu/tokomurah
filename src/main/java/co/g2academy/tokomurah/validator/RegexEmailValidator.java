
package co.g2academy.tokomurah.validator;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class RegexEmailValidator {
    private String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    
    public boolean emailPatternMatch(String email){
        return Pattern.matches(regex, email);
    }
}

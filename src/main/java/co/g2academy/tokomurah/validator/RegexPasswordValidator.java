
package co.g2academy.tokomurah.validator;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class RegexPasswordValidator {
    private String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public boolean passwordPatternMatch(String password){
        return Pattern.matches(regex, password);
    }
}

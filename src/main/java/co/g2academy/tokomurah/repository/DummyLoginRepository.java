package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.User;

/**
 *
 * @author ifnub
 */
public class DummyLoginRepository {
    private static User loggedInUser;
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        DummyLoginRepository.loggedInUser = loggedInUser;
    }
    
    
}

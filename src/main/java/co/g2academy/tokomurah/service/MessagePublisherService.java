package co.g2academy.tokomurah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class MessagePublisherService {
    
    @Autowired @Qualifier("redisPubSubTemplate")
    private RedisTemplate<String, String> template;
    
    public void publish(String message){
        template.convertAndSend(
                "orderMessagePubSub", message);
    }
    
}

package co.g2academy.tokomurah.service;

import co.g2academy.tokomurah.dto.OrderDto;
import co.g2academy.tokomurah.model.Order;
import co.g2academy.tokomurah.model.OrderItem;
import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.ShoppingCartItem;
import co.g2academy.tokomurah.repository.OrderRepository;
import co.g2academy.tokomurah.repository.ShoppingCartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnub
 */
@Service
public class CheckoutService {
    @Autowired private ShoppingCartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private MessagePublisherService messagePublisherService; 
    private ObjectMapper mapper = new JsonMapper();
    
    @Transactional(readOnly = false)
    public void checkout(ShoppingCart cart) throws JsonProcessingException{
        //1. update cart status
        cart.setStatus("PROCESSED");
        cart.setTransactionDate(new Date());
        cartRepository.save(cart);
        //2. insert order data
        Order order = new Order();
        order.setStatus("PROCESSED");
        order.setUser(cart.getUser());
        order.setTranscationDate(cart.getTransactionDate());
        Integer totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (ShoppingCartItem item : cart.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        //TODO 3. send order data to redis
        OrderDto dto = new OrderDto(order);
        String json = mapper.writeValueAsString(dto);
        //TODO send json to Redis pubsub
        System.out.println("Sending JSON to redis: " + json);
        messagePublisherService.publish(json);
    }
}

package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){

        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all){
            order.getMember().getName();    // Member 강제 초기화
            order.getDelivery().getAddress();   // Delivery 강제 초기화
            List<OrderItem> orderItems = order.getOrderItems(); // OrderItem 프록시 초기화
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());  // Items 강제 초기화
        }
        return all;
    }
}

package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDTO> findOrderQueryDTOs(){

        List<OrderQueryDTO> result = findOrders();
        // 컬렉션 부분은 여기서 따로 처리
        result.forEach(orderQueryDTO -> {
            List<OrderItemQueryDTO> orderItems = findOrderItems(orderQueryDTO.getOrderId());
            orderQueryDTO.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDTO> findOrderItems(Long orderId){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi join oi.item i where oi.order.id = :orderId", OrderItemQueryDTO.class)
                .setParameter("orderId", orderId).getResultList();
    }

    // 컬렉션 부분은 쿼리로 넣을 수가 없어서 일단은 따로 뺌
    private List<OrderQueryDTO> findOrders() {

        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o join o.member m join o.delivery d", OrderQueryDTO.class).getResultList();

    }
}

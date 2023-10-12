package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDTO> findOrderQueryDTOs() {

        List<OrderQueryDTO> result = findOrders();
        // 컬렉션 부분은 여기서 따로 처리
        result.forEach(orderQueryDTO -> {
            List<OrderItemQueryDTO> orderItems = findOrderItems(orderQueryDTO.getOrderId());
            orderQueryDTO.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDTO> findAllByDTO_optimization(){

        List<OrderQueryDTO> result = findOrders();  // 여기까진 똑같음

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDTO>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDTO>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDTO> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDTO.class)
                .setParameter("orderIds", orderIds).getResultList();

        // 키 : orderId, 값 : DTO
        Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDTO -> orderItemQueryDTO.getOrderId()));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDTO> result) {
        // orderId의 List가 됨
        List<Long> orderIds = result.stream()
                        .map(orderQueryDTO -> orderQueryDTO.getOrderId())
                                .collect(Collectors.toList());
        return orderIds;
    }


    private List<OrderItemQueryDTO> findOrderItems(Long orderId) {
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

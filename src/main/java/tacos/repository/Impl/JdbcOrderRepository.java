package tacos.repository.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.model.Order;
import tacos.model.Taco;
import tacos.repository.OrderRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());

        long orderId = saveOrderDetails(order);

        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        for (Taco taco : tacos) {
            saveTacoToOrder(orderId, taco);
        }
        return order;
    }

    /*
    1. convert an Order into a Map with ObjectMapper.
    2. put data, which will add to DB, into map
    3. execute method executeAndReturnKey to put data into DB and than DB will generate the id
    4. Now we know the ID of Order!!!
     */
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        return orderInserter.executeAndReturnKey(values).longValue();
    }

    /*
    1. Put into map orderId and tacoId (the map keys correspond to column names in the table)
    2. call to the orderTacoInserter’s execute() method performs the insert.
    The data has been added to DB
     */
    private void saveTacoToOrder(long orderId, Taco taco) {
        Map<String, Object> value = new HashMap<>();
        value.put("tacoOrder", orderId);
        value.put("taco", taco.getId());
        orderTacoInserter.execute(value);
    }
}

package org.example.proxishop.model.entities.shopkeeper;

import lombok.Setter;
import org.example.proxishop.model.entities.customer.Orders;

import java.util.List;

@Setter
public class UpdateOrderRequest {
    private List<Orders> orderList;

    public Orders[] getOrderList() {
        return orderList.toArray(new Orders[0]);
    }

}

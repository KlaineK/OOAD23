package controller;

import java.util.ArrayList;

import database.Connect;
import model.Cart;
import model.Order;
import model.OrderItem;

public class OrderItemController {
	OrderItem orderItem = new OrderItem();
	
	public void createOrderItem(String orderId, String itemId, String quantity) {
		
	}
	
	public String updateOrderItem(String orderItemId, String quantity, ArrayList<OrderItem> details, String orderId) {
		if(orderItemId.isEmpty() || quantity.isEmpty() || details.isEmpty() || orderId.isEmpty()) {
			return "Please select the order";
		}
		
		return orderItem.updateOrderItem(orderItemId, quantity, details, orderId);
	}
	
	public String updateExistingOrderItem(String userId, ArrayList<Cart> items, Order order) {
		return orderItem.updateExistingOrderItem(userId, items, order);
	}
	
	public String deleteOrderItem(OrderItem item) {
		if(item == null) {
			return "Please select an item";
		}
		
		return orderItem.deleteOrderItem(item);
	}
	
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(String orderId) {
		if(orderId.isEmpty()) {
			return null;
		}
		
		return orderItem.getAllOrderItemsByOrderId(orderId);
	}
}

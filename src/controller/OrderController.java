package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import database.Connect;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.User;

public class OrderController {
	//nanti ini dihapus, logic pindah ke model
	Connect db = Connect.getInstance();
	
	Order order = new Order();
	User user = null;
	
	public OrderController() {
		
	}
	
	public String createOrder(String userId, ArrayList<Cart> items, Integer total) {
		return order.createOrder(userId, items, total);
	}
	
	public void updateOrder(String orderId, ArrayList<OrderItem> items) {
		
	}
	
	public String deleteOrder(String orderId) {
		if(orderId.isEmpty()) {
			return "Please select an order";
		}
		
		return order.deleteOrder(orderId);
	}
	
	public void getOrdersByCustomerId() {
		
	}
	
	public String handleOrder(String orderId) {
		if(orderId.isEmpty()) {
			return "Please select an order";
		}
		
		return order.handleOrder(orderId);
	}
	
	public String serveOrder(String orderId) {
		if(orderId.isEmpty()) {
			return "Please select an order";
		}
		
		return order.serveOrder(orderId);
	}	
	
	public String updatePaidOrder(String orderId) {
		if(orderId.isEmpty()) {
			return "Please select an order";
		}
		
		return order.paidOrder(orderId);
	}	
	
	public void getSession(User u) {
		user = u;
	}
	
	public ArrayList<Order> getpaidOrders() {
		return order.getPaidOrder();
	}
	
	public ArrayList<Order> getAllOrders() {
		return order.getAllOrders(user);
	}
}

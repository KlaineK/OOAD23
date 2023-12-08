package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
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
	
	public void createOrder(String userId, ArrayList<OrderItem> items) {
		
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
	
	public void getSession(User u) {
		user = u;
	}
	
	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> orders = new ArrayList<>();
		
		if(user.getRole().equals("Chef")) {
			String query = String.format("SELECT `orders`.`orderId`, "
					+ "`orders`.`orderUserId`, `users`.`userName`, "
					+ "`orders`.`orderStatus`, `orders`.`orderTotal` "
					+ "FROM `orders` INNER JOIN `users` ON "
					+ "`orders`.`orderUserId` = `users`.`userId` "
					+ "WHERE `orders`.`orderStatus` = 'Pending'");
			ResultSet rs = db.selectData(query);
			
			try {
				while(rs.next()) {
					orders.add(new Order(Integer.toString(rs.getInt("orderId")), 
							Integer.toString(rs.getInt("orderUserId")), 
							rs.getString("userName"), rs.getString("orderStatus"), 
							rs.getInt("orderTotal")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (user.getRole().equals("Waiter")){
			String query = String.format("SELECT `orders`.`orderId`, "
					+ "`orders`.`orderUserId`, `users`.`userName`, "
					+ "`orders`.`orderStatus`, `orders`.`orderTotal` "
					+ "FROM `orders` INNER JOIN `users` ON "
					+ "`orders`.`orderUserId` = `users`.`userId` "
					+ "WHERE `orders`.`orderStatus` = 'Prepared'");
			ResultSet rs = db.selectData(query);
			
			try {
				while(rs.next()) {
					orders.add(new Order(Integer.toString(rs.getInt("orderId")),
							Integer.toString(rs.getInt("orderUserId")),
							rs.getString("userName"), rs.getString("orderStatus"),
							rs.getInt("orderTotal")));
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return orders;
	}
	
	public void getOrderByOrderId(String orderId) {
		
	}
}

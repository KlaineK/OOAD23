package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.Connect;
import main.Main;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.User;

public class OrderController {
	Connect db = Connect.getInstance();
	User user = null;
	
	public OrderController() {
		
	}
	
	public String createOrder(String userId, ArrayList<Cart> items, Integer total) {
		Integer orderId = 0;
		Boolean success = false;
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
		String queryOrder = String.format("INSERT INTO `orders` (`orderUserId`, `orderStatus`, `orderDate`, `orderTotal`) "
				+ "VALUES ('%s', '%s', '%s', '%d')", userId, "Pending", timestamp, total);
		
		System.out.println(queryOrder);
		
		if (db.execute(queryOrder)) {	
			String query = String.format("SELECT `orderId` FROM `orders` ORDER BY `orderId` DESC LIMIT 1");
			ResultSet rs = db.selectData(query);
			
			try {
				while(rs.next()) {
					orderId = rs.getInt("orderId");
				}
				
				if (orderId != 0) {
					for (Cart cart : items) {
						queryOrder = String.format("INSERT INTO `orderitems` (`orderId`, `menuItemId`, `quantity`) "
								+ "VALUES ('%d', '%d', '%d')", orderId, Integer.parseInt(cart.getMenuItem().getItemId()), cart.getQuantity());
						
						if (!db.execute(queryOrder)) {
							success = false;
							break;
						} else {
							success = true;
						}
					}
				}
				
				return success == true ? "Create Order Success" : "Create Order Failed";
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return "Create Order Failed";
	}
	
	public void updateOrder(String orderId, ArrayList<OrderItem> items) {
		
	}
	
	public String deleteOrder(String orderId) {
		
		String query = String.format("DELETE FROM `orderitems` WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(db.execute(query)) {
			query = String.format("DELETE FROM `orders` WHERE `orderId` = '%d'", Integer.parseInt(orderId));
			if(db.execute(query)) {
				return "Delete success";
			}
		}
		
		return "Delete failed";
		
	}
	
	public void getOrdersByCustomerId() {
		
	}
	
	public String handleOrder(String orderId) {
		String query = String.format("UPDATE `orders` SET `orderStatus` = 'Prepared' WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(!db.execute(query)) {
			return "Prepare failed";
		}
		
		return "Prepared";
	}
	
	public String serveOrder(String orderId) {
		String query = String.format("UPDATE `orders` SET `orderStatus` = 'Served' WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(!db.execute(query)) {
			return "Serving failed";
		}
		
		return "Order Served";
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
							Integer.toString(rs.getInt("orderTotal"))));
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
							Integer.toString(rs.getInt("orderTotal"))));
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

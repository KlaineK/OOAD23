package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import database.Connect;

public class Order {
	Connect db = Connect.getInstance();
	
	//attributes
	private String id;
	private String userId;
	private String name;
	private String status;
	private int total;
	
	//constructor to create an object
	public Order(String id, String userId, String name, String status, int total) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.status = status;
		this.total = total;
	}
	
	//empty construction for declaration
	public Order() {}
	
	//getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	//function to create a new order
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
	
	
	//function to delete an order by id
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
	
	//function to get orders from database by customer id
	
	//function to handle order by id
	public String handleOrder(String orderId) {
		String query = String.format("UPDATE `orders` SET `orderStatus` = 'Prepared' WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(!db.execute(query)) {
			return "Prepare failed";
		}
		
		return "Prepared";
	}
	
	//function to serve order by id
	public String serveOrder(String orderId) {
		String query = String.format("UPDATE `orders` SET `orderStatus` = 'Served' WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(!db.execute(query)) {
			return "Serving failed";
		}
		
		return "Order Served";
	}	
	
	//function to serve order by id
	public String paidOrder(String orderId) {
		String query = String.format("UPDATE `orders` SET `orderStatus` = 'Paid' WHERE `orderId` = '%d'", Integer.parseInt(orderId));
		if(!db.execute(query)) {
			return "Update order to paid failed";
		}
		
		return "Order Paid";
	}
	
	//function to get order with paid status
	public ArrayList<Order> getPaidOrder() {
		ArrayList<Order> orders = new ArrayList<>();
		
		//query for select order with paid status
		String query = String.format("SELECT `orders`.`orderId`, "
				+ "`orders`.`orderUserId`, `users`.`userName`, "
				+ "`orders`.`orderStatus`, `orders`.`orderTotal` "
				+ "FROM `orders` INNER JOIN `users` ON "
				+ "`orders`.`orderUserId` = `users`.`userId` "
				+ "WHERE `orders`.`orderStatus` = 'Paid'");
		ResultSet rs = db.selectData(query);
		
		try {
			while(rs.next()) {
				orders.add(new Order(Integer.toString(rs.getInt("orderId")), 
						Integer.toString(rs.getInt("orderUserId")), 
						rs.getString("userName"), rs.getString("orderStatus"), 
						rs.getInt("orderTotal")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	
	public ArrayList<Order> getAllOrders(User user){
		ArrayList<Order> orders = new ArrayList<>();
		
		String status = "";
		
		if(user.getRole().equals("Chef")) {
			status = "Pending";
		} else if (user.getRole().equals("Waiter")) {
			status = "Prepared";
		} else if (user.getRole().equals("Cashier")){
			status = "Served";
		}
		
		String query = String.format("SELECT `orders`.`orderId`, "
				+ "`orders`.`orderUserId`, `users`.`userName`, "
				+ "`orders`.`orderStatus`, `orders`.`orderTotal` "
				+ "FROM `orders` INNER JOIN `users` ON "
				+ "`orders`.`orderUserId` = `users`.`userId` "
				+ "WHERE `orders`.`orderStatus` = '" + status + "'");
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
		
		return orders;

	}
}

package model;

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
	
	//function to get order from database by order id
	
}

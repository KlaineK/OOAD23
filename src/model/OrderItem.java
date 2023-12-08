package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class OrderItem {
	Connect db = Connect.getInstance();
	
	//attributes
	private String id;
	private String orderId;
	private String itemId;
	private String itemName;
	private int quantity;
	private int price;
	
	//constructor to create object
	public OrderItem(String id, String orderId, String itemId, String itemName, int quantity, int price) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}
	
	//empty constructor for declaration
	public OrderItem() {}
	
	//getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	//function to create order item
	
	//function to update order item
	public String updateOrderItem(String orderItemId, String quantity, ArrayList<OrderItem> details, String orderId) {
		String query = String.format("UPDATE `orderitems` SET `quantity` = '%d' WHERE `orderItemsId` = '%d'", Integer.parseInt(quantity), Integer.parseInt(orderItemId));
		
		//calculation for total column by multiplying price and quantity
		if(db.execute(query)) {
			int total = 0;
			for (OrderItem d : details) {
				if(d.getId().equals(orderItemId)) {
					total  += (Integer.parseInt(quantity) * d.getPrice());
				}
				else
				{
					total += (d.getQuantity() * d.getPrice());					
				}
			}
			query = String.format("UPDATE `orders` SET `orderTotal` = '%d' WHERE `orderId` = '%d'", total, Integer.parseInt(orderId));
			if(db.execute(query)) {
				return "Update success";				
			}
		}
		return "Update failed";
	}
	
	//function to delete order item by id
	public String deleteOrderItem(OrderItem item) {
		String query = String.format("DELETE FROM `orderitems` WHERE `orderItemsId` = '%d'", Integer.parseInt(item.getId()));
		
		if(db.execute(query)) {
			ArrayList<OrderItem> items = getAllOrderItemsByOrderId(item.getOrderId());
			
			//if all order items are deleted then delete the order header
			if(items.isEmpty()) {
				query = String.format("DELETE FROM `orders` WHERE `orderId` = '%d'", Integer.parseInt(item.getOrderId()));
				if(db.execute(query)) {
					return "Delete success";
				}
			}
			//if not then calculate the current price by multiplying price and current quantity
			else {
				int total = 0;
				for (OrderItem o : items) {
					total += (o.getQuantity() * o.getPrice());
				}
				
				query = String.format("UPDATE `orders` SET `orderTotal` = '%d' WHERE `orderId` = '%d'", total, Integer.parseInt(item.getOrderId()));
				if(db.execute(query)) {
					return "Delete success";				
				}
			}
		}
		return "Delete failed";
	}
	
	//function to get order items by order id
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(String orderId) {
		String query = String.format("SELECT `orderitems`.`orderItemsId`, "
				+ "`orderitems`.`orderId`, `orderitems`.`menuItemId`, "
				+ "`menuitems`.`menuItemName`, `orderitems`.`quantity`, `menuitems`.`menuItemPrice`"
				+ "FROM `orderitems` INNER JOIN `menuitems` ON "
				+ "`orderitems`.`menuItemId` = `menuitems`.`menuItemId` "
				+ "WHERE `orderitems`.`orderId` = '%d'", Integer.parseInt(orderId));
		ArrayList<OrderItem> items = new ArrayList<>();
		ResultSet rs = db.selectData(query);
		
		try {
			while(rs.next()) {
				items.add(new OrderItem(Integer.toString(rs.getInt("orderItemsId")), 
						Integer.toString(rs.getInt("orderId")), Integer.toString(rs.getInt("menuItemId")), 
						rs.getString("menuItemName"), rs.getInt("quantity"), rs.getInt("menuItemPrice")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return items;
	}
	
}

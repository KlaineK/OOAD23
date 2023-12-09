package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import model.Cart;
import model.Order;
import model.OrderItem;

public class OrderItemController {
	Connect db = Connect.getInstance();
	
	public void createOrderItem(String orderId, String itemId, String quantity) {
		
	}
	
	public String updateOrderItem(String orderItemId, String quantity, ArrayList<OrderItem> details, String orderId) {
		String query = String.format("UPDATE `orderitems` SET `quantity` = '%d' WHERE `orderItemsId` = '%d'", Integer.parseInt(quantity), Integer.parseInt(orderItemId));
		if(db.execute(query)) {
			int total = 0;
			for (OrderItem d : details) {
				if(d.getId().equals(orderItemId)) {
					total  += (Integer.parseInt(quantity) * Integer.parseInt(d.getPrice()));
				}
				else
				{
					total += (Integer.parseInt(d.getQuantity()) * Integer.parseInt(d.getPrice()));					
				}
			}
			query = String.format("UPDATE `orders` SET `orderTotal` = '%d' WHERE `orderId` = '%d'", total, Integer.parseInt(orderId));
			if(db.execute(query)) {
				return "Update success";				
			}
		}
		return "Update failed";
	}
	
	public String deleteOrderItem(OrderItem item) {
		String query = String.format("DELETE FROM `orderitems` WHERE `orderItemsId` = '%d'", Integer.parseInt(item.getId()));
		if(db.execute(query)) {
			ArrayList<OrderItem> items = getAllOrderItemsByOrderId(item.getOrderId());
			
			if(items.isEmpty()) {
				query = String.format("DELETE FROM `orders` WHERE `orderId` = '%d'", Integer.parseInt(item.getOrderId()));
				if(db.execute(query)) {
					return "Delete success";
				}
			}
			else {
				int total = 0;
				for (OrderItem o : items) {
					total += (Integer.parseInt(o.getQuantity()) * Integer.parseInt(o.getPrice()));
				}
				
				query = String.format("UPDATE `orders` SET `orderTotal` = '%d' WHERE `orderId` = '%d'", total, Integer.parseInt(item.getOrderId()));
				if(db.execute(query)) {
					return "Delete success";				
				}
			}
		}
		return "Delete failed";
	}
	
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
						rs.getString("menuItemName"), Integer.toString(rs.getInt("quantity")), Integer.toString(rs.getInt("menuItemPrice"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return items;
	}
}

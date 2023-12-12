package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class MenuItem {
	Connect db = Connect.getInstance();
	
	//attributes
	private String itemId;
	private String name;
	private String description;
	private int price;
	
	//constructor to create an object
	public MenuItem(String itemId, String name, String description, int price) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	//empty constructor for declaration
	public MenuItem() {}
	
	//getters and setters
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	//function to get all menu item from database
	public ArrayList<MenuItem> getAllMenuItem() {
		String query = String.format("SELECT * FROM `menuitems`");

		ResultSet res = db.selectData(query);
		ArrayList<MenuItem> items = new ArrayList<>();
		
		try {
			while(res.next()) {
				MenuItem item = new MenuItem(Integer.toString(res.getInt("menuItemId")), 
						res.getString("menuItemName"), res.getString("menuItemDescription"), 
						res.getInt("menuItemPrice"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	//function to create new menu item
	public String createMenuItem(String name, String desc, String price) {

		String query = String.format("INSERT INTO `menuitems` (`menuItemName`, `menuItemDescription`, `menuItemPrice`)"
				+ "VALUES ('%s', '%s', '%d')", name, desc, Integer.parseInt(price));
		if(!db.execute(query)) {
			return "Add Menu Item Failed";
		}
		
		return "Success adding new menu item";
	}
	
	//function to update menu item by id
	public String updateMenuItem(String id, String name, String desc, String price) {
		
		String query = String.format("UPDATE `menuitems` SET `menuItemName` = '%s', `menuItemDescription` = '%s', "
				+ "`menuItemPrice` = '%d' WHERE `menuItemId` = '%d'", name, desc, Integer.parseInt(price), Integer.parseInt(id));
		
		if(!db.execute(query)) {
			return "Update failed";
		}
		
		return "Update success";
	}
	
	//function to delete menu item by id
	public String deleteMenuItem(String id) {
		String query = String.format("DELETE FROM `menuitems` WHERE `menuItemId` = '%d'", Integer.parseInt(id));
		
		if(!db.execute(query)) {
			return "Delete failed";
		}
		return "Delete success";
	}
}

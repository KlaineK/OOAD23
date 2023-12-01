package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import model.MenuItem;

public class MenuItemController {
	Connect db = Connect.getInstance();
	
	public ArrayList<MenuItem> getAllMenuItem() {
		String query = String.format("SELECT * FROM `menuitems`");

		ResultSet res = db.selectData(query);
		ArrayList<MenuItem> items = new ArrayList<>();
		
		try {
			while(res.next()) {
				MenuItem item = new MenuItem(Integer.toString(res.getInt("menuItemId")), 
						res.getString("menuItemName"), res.getString("menuItemDescription"), 
						Integer.toString(res.getInt("menuItemPrice")));
				items.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return items;
	}
	
	public String createMenuItem(String name, String desc, String price) {
		if(Double.parseDouble(price) < 2.5) {
			return "Price must be at least 2.5";
		}
		
		String query = String.format("INSERT INTO `menuitems` (`menuItemName`, `menuItemDescription`, `menuItemPrice`)"
				+ "VALUES ('%s', '%s', '%d')", name, desc, Integer.parseInt(price));
		if(!db.execute(query)) {
			return "Add Menu Item Failed";
		}
		
		return "Success adding new menu item";
	}
	
	public String updateMenuItem(String id, String name, String desc, String price) {
		
		if(Integer.parseInt(price) < 2.5){
			return "Price must be at least 2.5";
		}
		
		String query = String.format("UPDATE `menuitems` SET `menuItemName` = '%s', `menuItemDescription` = '%s', "
				+ "`menuItemPrice` = '%d' WHERE `menuItemId` = '%d'", name, desc, Integer.parseInt(price), Integer.parseInt(id));
		
		if(!db.execute(query)) {
			return "Update failed";
		}
		
		return "Update success";
	}
	
	public String deleteMenuItem(String id) {
		String query = String.format("DELETE FROM `menuitems` WHERE `menuItemId` = '%d'", Integer.parseInt(id));
		
		if(!db.execute(query)) {
			return "Delete failed";
		}
		return "Delete success";
	}
}

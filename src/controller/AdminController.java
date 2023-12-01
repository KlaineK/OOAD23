package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import model.MenuItem;
import model.User;

public class AdminController {
	Connect db = Connect.getInstance();
	
	public ArrayList<User> getUserList() {
		String query = String.format("SELECT * FROM `users`");
		ResultSet res = db.selectData(query);
		
		ArrayList<User> users = new ArrayList<>();

		try {
			while(res.next()) {
				User u = new User(Integer.toString(res.getInt("userId")), res.getString("userName"), res.getString("userEmail"), res.getString("userPassword"), res.getString("userRole"));
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public String editRole(String role, String userId) {
		String query = String.format("UPDATE `users` SET `userRole` = '%s' 	WHERE `userId` = '%d'", role, Integer.parseInt(userId));
		Boolean res = db.execute(query);
		if(!res) {
			return "Failed";
		}
		return "Success";
	}
	
	public ArrayList<MenuItem> getMenuItemList() {
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
	
	public String deleteUser(String userId) {
		String query = String.format("DELETE FROM `users` WHERE `userId` = '%d'", Integer.parseInt(userId));
		Boolean res = db.execute(query);
		if(!res) {
			return "Failed";
		}
		
		return "Success";
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
}

package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {
	MenuItem menuItem = new MenuItem();
	
	public ArrayList<MenuItem> getAllMenuItem() {
		return menuItem.getAllMenuItem();
	}
	
	public String createMenuItem(String name, String desc, String price) {
		if(name.isEmpty() || desc.isEmpty() || price.isEmpty()) {
			return "Please fill all the fields";
		}
		else if(Double.parseDouble(price) < 2.5) {
			return "Price must be at least 2.5";
		}
		
		return menuItem.createMenuItem(name, desc, price);
	}
	
	public String updateMenuItem(String id, String name, String desc, String price) {
		if(id.isEmpty() || name.isEmpty() || desc.isEmpty() || price.isEmpty()) {
			return "Plase fill all the fields";
		}
		else if(Integer.parseInt(price) < 2.5){
			return "Price must be at least 2.5";
		}
		
		return menuItem.updateMenuItem(id, name, desc, price);
	}
	
	public String deleteMenuItem(String id) {
		return menuItem.deleteMenuItem(id);
	}
}

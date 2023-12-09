package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import view.AdminPage;
import view.CashierPage;
import view.ChefPage;
import view.CustomerPage;
import view.Login;
import view.WaiterPage;

public class User {
	Connect db = Connect.getInstance();
	
	//attributes
	private String id;
	private String username;
	private String email;
	private String password;
	private String role;
	
	//constructor to create object
	public User(String id, String username, String email, String password, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	//empty constructor for declaration
	public User() {};
	
	//getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	//functions
	public String deleteUser(String userId) {
		String query = String.format("DELETE FROM `users` WHERE `userId` = '%d'", Integer.parseInt(userId));

		if(!db.execute(query)) {
			return "Failed";
		}
		
		return "Success";
	}
	
	//create new user function
	public String createUser(String username, String email, String password, String confirmPassword, Stage primaryStage, Main main) {
		String query = String.format("INSERT INTO `users` (`userRole`, `userName`, `userEmail`, `userPassword`) "
				+ "VALUES ('%s', '%s', '%s', '%s')", "Customer", username, email, password);
		if(!db.execute(query)) {
			return "Email already exist";
		}
		
		primaryStage.setScene(new Scene(new Login(primaryStage, main), 1200, 700));
		return "Success";
	}
	
	//get all users from database
	public ArrayList<User> getAllUser() {
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
	
	//update user function
	public String updateUser(String role, String userId) {
		String query = String.format("UPDATE `users` SET `userRole` = '%s' 	WHERE `userId` = '%d'", role, Integer.parseInt(userId));
		
		if(!db.execute(query)) {
			return "Failed";
		}
		return "Success";
	}
	
	//authenticate function
	public String authenticate(String email, String password, Stage primaryStage, Main main) {
		String query = String.format("SELECT * FROM `users` WHERE `userEmail` = ? AND `userPassword` = ?");
		PreparedStatement ps = db.prepStatement(query);
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				User user = new User(Integer.toString(rs.getInt("userId")), rs.getString("userName"), rs.getString("userEmail"), rs.getString("userPassword"), rs.getString("userRole"));
				main.setSession(user);
				
				if(user.getRole().equals("Customer")) {
					primaryStage.setScene(new Scene(new CustomerPage(primaryStage, main), 1200, 700));					
				}
				else if(user.getRole().equals("Admin")) {
					primaryStage.setScene(new Scene(new AdminPage(primaryStage, main), 1200, 700));
				}
				else if(user.getRole().equals("Chef")) {
					primaryStage.setScene(new Scene(new ChefPage(primaryStage, main), 1200, 700));
				}
				else if(user.getRole().equals("Cashier")) {
					primaryStage.setScene(new Scene(new CashierPage(primaryStage, main), 1200, 700));
				}
				else if(user.getRole().equals("Waiter")) {
					primaryStage.setScene(new Scene(new WaiterPage(primaryStage, main), 1200, 700));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Failed";
	}
	
	
}

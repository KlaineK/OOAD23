package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import view.AdminPage;
import view.CashierPage;
import view.ChefPage;
import view.CustomerPage;
import view.Login;
import view.WaiterPage;

public class UserController {
	Connect db = Connect.getInstance();
	
	public String registerUser(String username, String email, String password, String confirmPassword, Stage primaryStage) {
		if(password.length() < 6) {
			return "Password must be at least 6 characters";
		}
		else if(!password.equals(confirmPassword)) {
			return "Password confirmation doesn't match";
		}
		
		String query = String.format("INSERT INTO `users` (`userRole`, `userName`, `userEmail`, `userPassword`) "
				+ "VALUES ('%s', '%s', '%s', '%s')", "Customer", username, email, password);
		if(!db.execute(query)) {
			return "Email already exist";
		}
		
		primaryStage.setScene(new Scene(new Login(primaryStage), 1200, 700));
		return "Success";
	}
	
	public String login(String username, String password, Stage primaryStage) {
		String query = String.format("SELECT * FROM `users` WHERE `userEmail` = '%s' AND `userPassword` = '%s'", username, password);
		ResultSet rs = db.selectData(query);
		try {
			if(rs.next()) {
				System.out.println(rs.getString("userEmail"));
				System.out.println(rs.getString("userPassword"));
				User user = new User(rs.getString("userName"), rs.getString("userEmail"), rs.getString("userPassword"), rs.getString("userRole"));
				System.out.println(user.getUsername());
				System.out.println(user.getPassword());
				
				if(user.getRole().equals("Customer")) {
					primaryStage.setScene(new Scene(new CustomerPage(primaryStage), 1200, 700));					
				}
				else if(user.getRole().equals("Admin")) {
					primaryStage.setScene(new Scene(new AdminPage(primaryStage), 1200, 700));
				}
				else if(user.getRole().equals("Chef")) {
					primaryStage.setScene(new Scene(new ChefPage(primaryStage), 1200, 700));
				}
				else if(user.getRole().equals("Cashier")) {
					primaryStage.setScene(new Scene(new CashierPage(primaryStage), 1200, 700));
				}
				else if(user.getRole().equals("Waiter")) {
					primaryStage.setScene(new Scene(new WaiterPage(primaryStage), 1200, 700));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}

}

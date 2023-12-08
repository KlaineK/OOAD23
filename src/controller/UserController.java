package controller;

import java.util.ArrayList;

import database.Connect;
import javafx.stage.Stage;
import main.Main;
import model.User;

public class UserController {
	User user = new User();
	Connect db = Connect.getInstance();
	
	public String createUser(String username, String email, String password, String confirmPassword, Stage primaryStage, Main main) {
		if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			return "Please fill all the fields";
		}
		else if(password.length() < 6) {
			return "Password must be at least 6 characters";
		}
		else if(!password.equals(confirmPassword)) {
			return "Password confirmation doesn't match";
		}
		
		return user.createUser(username, email, password, confirmPassword, primaryStage, main);
	}
	
	public String deleteUser(String userId) {
		if(userId.isEmpty()) {
			return "Please fill all the fields";
		}

		return user.deleteUser(userId);
	}
	
	public ArrayList<User> getAllUser() {
		return user.getAllUser();
	}
	
	public String updateUser(String role, String userId) {
		if(role.isEmpty() || userId.isEmpty()) {
			return "Please fill all the fields";
		}
		
		return user.updateUser(role, userId);
	}
	
	
	public String authenticate(String email, String password, Stage primaryStage, Main main) {
		if(email.isEmpty() || password.isEmpty()) {
			return "Please fill all the fields";
		}
		
		return user.authenticate(email, password, primaryStage, main);
	}

}

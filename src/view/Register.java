package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.Main;

public class Register extends BorderPane {
	public Register (Stage primaryStage, Main main) {
		VBox container = new VBox();
		GridPane gp = new GridPane();
		
		Label uname = new Label("Username");
		Label email = new Label("Email");
		Label password = new Label("Password");
		Label confirmPass = new Label("Confirm Password");
		Label regisTitle = new Label("Register");
		Label login = new Label("Already have an account?");
		Label warning = new Label();
		
		Button regisBtn = new Button("Register");
		
		TextField unameField = new TextField();
		TextField emailField = new TextField();
		PasswordField passField = new PasswordField();
		PasswordField confirmPassField = new PasswordField();
		
		gp.setMinSize(400, 150);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		gp.setHgap(20);
		
		gp.add(uname, 0, 0);
		gp.add(unameField, 1, 0);
		gp.add(email, 0, 1);
		gp.add(emailField, 1, 1);
		gp.add(password, 0, 2);
		gp.add(passField, 1, 2);
		gp.add(confirmPass, 0, 3);
		gp.add(confirmPassField, 1, 3);
		
		container.getChildren().addAll(regisTitle, gp, warning, login, regisBtn);
		setCenter(container);
		
		regisTitle.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		unameField.setMaxWidth(200);
		passField.setMaxWidth(200);
		confirmPassField.setMaxWidth(200);
		emailField.setMaxWidth(200);
		VBox.setMargin(regisTitle, new Insets(0, 0, 30, 0));
		VBox.setMargin(login, new Insets(0, 0, 20, 0));
		VBox.setMargin(warning, new Insets(5, 0, 5, 0));
		container.setAlignment(Pos.CENTER);
		login.setUnderline(true);
		regisBtn.setPrefWidth(80);
		
		login.setOnMouseClicked(e -> {
			primaryStage.setScene(new Scene(new Login(primaryStage, main), 1200, 700));
		});
		
		regisBtn.setOnMouseClicked(e -> {
			if(emailField.getText().isBlank() || passField.getText().isBlank() || confirmPassField.getText().isBlank() || unameField.getText().isBlank()) {
				warning.setText("Please fill all the fields");
			}
			else {
				UserController userController = new UserController();
				String res = userController.registerUser(unameField.getText(), emailField.getText(), passField.getText(), confirmPassField.getText(), primaryStage, main);
				warning.setText(res);
			}
		});
	}
}

package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class Login extends BorderPane {
	public Login(Stage primaryStage) {
		VBox container = new VBox();
		GridPane gp = new GridPane();
		
		Label loginTitle = new Label("Login");
		Label email = new Label("Email");
		Label password = new Label("Password");
		Label warning = new Label();
		
		Button loginBtn = new Button("Login");
		
		TextField emailField = new TextField();
		PasswordField passField = new PasswordField();
		
		gp.add(email, 0, 0);
		gp.add(emailField, 1, 0);
		gp.add(password, 0, 1);
		gp.add(passField, 1, 1);
		container.getChildren().addAll(loginTitle, gp, warning, loginBtn);
		setCenter(container);
		
		gp.setMinSize(400, 80);
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(20);
		gp.setVgap(10);
		
		emailField.setMaxWidth(200);
		passField.setMaxWidth(200);
		VBox.setMargin(loginTitle, new Insets(0, 0, 30, 0));
		VBox.setMargin(warning, new Insets(5, 0, 5, 0));
		loginTitle.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		container.setAlignment(Pos.CENTER);
		
		loginBtn.setOnMouseClicked(e -> {
			if(emailField.getText().isEmpty() || passField.getText().isEmpty()) {
				warning.setText("Please fill all the fields");
			}
			else {
				UserController userController = new UserController();
				String res = userController.login(emailField.getText(), passField.getText(), primaryStage);
				warning.setText(res);
			}
		});
	}
}

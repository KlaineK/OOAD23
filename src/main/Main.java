package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import view.AdminPage;
import view.ChefPage;
import view.ReceiptPage;
import view.Register;

public class Main extends Application {

	private User session = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
//		primaryStage.setScene(new Scene(new ChefPage(primaryStage, this), 1200, 700));
//		primaryStage.setScene(new Scene(new ReceiptPage(primaryStage, this), 1200, 700));
		primaryStage.setScene(new Scene(new Register(primaryStage, this), 1200, 700));
		primaryStage.show();
	}
	
	public User getSession() {
		return session;
	}
	
	public void setSession(User user) {
		session = user;
	}
}

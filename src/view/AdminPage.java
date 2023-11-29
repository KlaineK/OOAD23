package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminPage extends BorderPane {
	Menu menu;
	MenuBar mb;
	MenuItem m1, m2, m3;
	VBox container;
	BorderPane bp;
	Label title;
	
	private void menu() {
		System.out.println("Admin page");
		menu = new Menu("Menu");
		m1 = new MenuItem("Manage Users");
		m2 = new MenuItem("Manage Menu Items");
		m3 = new MenuItem("Logout");
		mb = new MenuBar();
		container = new VBox();
		bp = new BorderPane();
		title = new Label("Manage User");
		bp = new BorderPane();
		
		menu.getItems().addAll(m1, m2, m3);
		mb.getMenus().add(menu);
		container.getChildren().add(title);
		bp.setTop(mb);
		bp.setCenter(container);
		setCenter(bp);

		VBox.setMargin(title, new Insets(10));
		title.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
		container.setAlignment(Pos.TOP_CENTER);
		setCenter(container);
		
	}
	
	private void manageUser() {
		title.setText("Manage User Menu");
	}
	
	private void manageMenu() {
		title.setText("Manage Menu Items Menu");
	}
	
	public AdminPage(Stage primaryStage) {
		primaryStage.setTitle("Admin Page");
		
		menu();
		
		m1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				manageUser();
			}
		});
		
		m2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				manageMenu();
			}
		});
		
		m3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage current = (Stage) bp.getScene().getWindow();
				current.close();
			}
		});
		
	}

}

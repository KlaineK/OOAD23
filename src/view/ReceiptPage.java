package view;

import java.util.ArrayList;

import controller.OrderController;
import controller.OrderItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.Main;
import model.Order;
import model.OrderItem;

public class ReceiptPage extends BorderPane {
	
	private OrderController oc = new OrderController();
	private OrderItemController oic = new OrderItemController();
	
	Menu menu;
	MenuBar mb;
	MenuItem m1, m2, m3;
	VBox mainbox, orderContainer, receiptContainer;
	BorderPane bp;
	GridPane gpOrder;
	Label orderTitle, receiptTitle, mainTitle, idLabel, nameLabel, totalLabel;
	TableView orderTable, receiptTable;
	TableColumn orderId, orderName, orderStatus, orderTotal;
	TableColumn detailOrderId, detailItemName, detailQuantity, detailId, detailItemId, detailItemPrice;
	ScrollPane spOrder, spDetail;
	
	private void menu() {
		//initializing menu bar
		menu = new Menu("Menu");
		m1 = new MenuItem("View Ongoing/Served Order");
		m2 = new MenuItem("View receipt");
		m3 = new MenuItem("Log out");
		mb = new MenuBar();
		
		//initializing container, grid pane, border pane, and border pane
		mainbox = new VBox();
		orderContainer = new VBox();
		receiptContainer = new VBox();
		bp = new BorderPane();
		gpOrder = new GridPane();
		
		//initializing label
		orderTitle = new Label("Orders");
		receiptTitle = new Label("Receipt Detail");
		mainTitle = new Label("Receipts");
		idLabel = new Label("Order id");
		nameLabel = new Label("Order name");
		totalLabel = new Label("Order total");
		
		//initializing table view
		orderTable = new TableView<>();
		receiptTable = new TableView<>();
		
		orderId = new TableColumn("ID");
		orderName = new TableColumn("Name");
		orderStatus = new TableColumn("Status");
		orderTotal = new TableColumn("Total");
		
		detailId = new TableColumn("Detail ID");
		detailOrderId = new TableColumn("Order ID");
		detailItemId = new TableColumn("Item ID");
		detailItemName = new TableColumn("Item Name");
		detailQuantity = new TableColumn("Quantity");
		detailItemPrice = new TableColumn("Price/Item");	
		
		//initialziing scroll pane
		spOrder = new ScrollPane();
		spDetail = new ScrollPane();
		
		manageMenu();
	}
	
	private void manageMenu() {
		//setup menu
		menu.getItems().addAll(m1, m2, new SeparatorMenuItem(), m3);
		mb.getMenus().add(menu);
		
		//setup order table
		orderTable.getColumns().addAll(orderId, orderName, orderStatus, orderTotal);
		orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
		orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
		orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		orderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		orderId.setStyle("-fx-alignment: CENTER");
		orderName.setStyle("-fx-alignment: CENTER");
		orderStatus.setStyle("-fx-alignment: CENTER");
		orderTotal.setStyle("-fx-alignment: CENTER");

		orderId.prefWidthProperty().bind(orderTable.widthProperty().divide(4));
		orderName.prefWidthProperty().bind(orderTable.widthProperty().divide(4));
		orderStatus.prefWidthProperty().bind(orderTable.widthProperty().divide(4));
		orderTotal.prefWidthProperty().bind(orderTable.widthProperty().divide(4));
		
		spOrder.setContent(orderTable);
		
		//setup receipt table
		receiptTable.getColumns().addAll(detailId, detailOrderId, detailItemId, detailItemName, detailQuantity,
				detailItemPrice);
		detailId.setCellValueFactory(new PropertyValueFactory("id"));
		detailOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
		detailItemId.setCellValueFactory(new PropertyValueFactory("itemId"));
		detailItemName.setCellValueFactory(new PropertyValueFactory("itemName"));
		detailQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
		detailItemPrice.setCellValueFactory(new PropertyValueFactory("price"));

		detailId.setStyle("-fx-alignment: CENTER");
		detailOrderId.setStyle("-fx-alignment: CENTER");
		detailItemId.setStyle("-fx-alignment: CENTER");
		detailItemName.setStyle("-fx-alignment: CENTER");
		detailQuantity.setStyle("-fx-alignment: CENTER");
		detailItemPrice.setStyle("-fx-alignment: CENTER");

		detailOrderId.prefWidthProperty().bind(receiptTable.widthProperty().divide(6));
		detailItemName.prefWidthProperty().bind(receiptTable.widthProperty().divide(6));
		detailQuantity.prefWidthProperty().bind(receiptTable.widthProperty().divide(6));

		spDetail.setContent(receiptTable);
		
		//setup grid pane for main container
		gpOrder.add(orderContainer, 0, 0);
		gpOrder.add(receiptContainer, 1, 0);
		
		//setup container for receipt detail
		receiptContainer.getChildren().addAll(receiptTitle, idLabel, nameLabel, totalLabel, receiptTable);
		
		//setup container for order
		orderContainer.getChildren().addAll(orderTitle, orderTable);
		
		//setup main container
		mainbox.getChildren().addAll(mainTitle, gpOrder);
		
		//setup border pane
		bp.setTop(mb);
		bp.setCenter(mainbox);
		setCenter(bp);
		
		//vbox margin
		VBox.setMargin(orderTitle, new Insets(0, 0, 10, 10));
		VBox.setMargin(receiptTitle, new Insets(0, 0, 10, 0));

		// styling
		mainTitle.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 26));
		orderTitle.setFont(Font.font("Calibri", FontWeight.BLACK, FontPosture.REGULAR, 19));
		receiptTitle.setFont(Font.font("Calibri", FontWeight.BLACK, FontPosture.REGULAR, 19));
		mainTitle.setMinHeight(80);
		
		mainbox.setAlignment(Pos.TOP_CENTER);
		orderTable.setPrefSize(480, 500);
		receiptTable.setPrefSize(480, 400);

		gpOrder.setAlignment(Pos.CENTER);
		gpOrder.setMinSize(1000, 500);
		gpOrder.setHgap(50);
		
		VBox.setMargin(idLabel, new Insets(7, 0, 7, 0));
		VBox.setMargin(nameLabel, new Insets(7, 0, 7, 0));
		VBox.setMargin(totalLabel, new Insets(7, 0, 10, 0));
		
		idLabel.setFont(Font.font("Calibri", FontWeight.LIGHT, FontPosture.REGULAR, 16));
		nameLabel.setFont(Font.font("Calibri", FontWeight.LIGHT, FontPosture.REGULAR, 16));
		totalLabel.setFont(Font.font("Calibri", FontWeight.LIGHT, FontPosture.REGULAR, 16));
		
	}
	
	private void viewReceipt() {
		orderTable.getItems().clear();
		receiptTable.getItems().clear();
		
		ArrayList<Order> orders = oc.getpaidOrders();
		
		for (Order o : orders) {
			orderTable.getItems().add(new Order(o.getId(), o.getUserId(), o.getName(), o.getStatus(), o.getTotal()));
		}
		
		orderTable.setOnMouseClicked(e -> {
			receiptTable.getItems().clear();
			Order currorder = (Order) orderTable.getSelectionModel().getSelectedItem();

			idLabel.setText("Order ID: " + currorder.getId());
			nameLabel.setText("Order name: " + currorder.getName());
			totalLabel.setText("Total: " + currorder.getTotal());
			
			ArrayList<OrderItem> details = oic.getAllOrderItemsByOrderId(currorder.getId());

			for (OrderItem d : details) {
				receiptTable.getItems().add(new OrderItem(d.getId(), d.getOrderId(), d.getItemId(), d.getItemName(),
						d.getQuantity(), d.getPrice()));
			}
		});
	}
	
	public ReceiptPage(Stage primaryStage, Main main) {
		primaryStage.setTitle("Receipt");
		
		menu();
		viewReceipt();
		
		m1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(new Scene(new CashierPage(primaryStage, main), 1200, 700));
			}
		});
		
		m2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(new Scene(new CashierPage(primaryStage, main), 1200, 700));
			}
		});
		
		m3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				main.setSession(null);
				primaryStage.setScene(new Scene(new Register(primaryStage, main), 1200, 700));
			}
		});
	}

}

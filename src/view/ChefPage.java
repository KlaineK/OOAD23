package view;

import java.util.ArrayList;

import controller.OrderController;
import controller.OrderItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;
import main.Main;
import model.Order;
import model.OrderItem;

public class ChefPage extends BorderPane {
	OrderController orderController = new OrderController();
	OrderItemController orderItemController = new OrderItemController();
	
	Menu menu;
	MenuBar mb;
	MenuItem m1, m2;
	VBox mainBox, container1, container2;
	BorderPane bp;
	GridPane gpOrder, gpForm;
	Label title, formTitle, orderTitle, detailTitle;
	Label itemLabel, itemField, qtyLabel, warning;
	Spinner<Integer> qtySpinner;
	Button updateBtn;
	TableView orderTable, detailTable;
	TableColumn orderId, orderName, orderStatus, orderTotal, orderUserId;
	TableColumn detailOrderId, detailItemName, detailQuantity, detailId, detailItemId, detailItemPrice;
	ScrollPane spOrder, spDetail;
	
	private void addOrderDeleteButton() {
        TableColumn<Order, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {

                    private final Button deleteBtn = new Button("Delete");

                    {
                        deleteBtn.setOnAction((ActionEvent event) -> {
                        	Order order = getTableView().getItems().get(getIndex());
                        	String res = orderController.deleteOrder(order.getId());
                        	System.out.println(res);
                        	
                        	handleOrder();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteBtn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        orderTable.getColumns().add(colBtn);

    }
	
	private void addOrderHandleButton() {
        TableColumn<Order, Void> colBtn = new TableColumn("Handle");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {

                    private final Button btn = new Button("Handle");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order item = getTableView().getItems().get(getIndex());
                            String res = orderController.handleOrder(item.getId());
                            System.out.println(res);
                            
                            handleOrder();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        orderTable.getColumns().add(colBtn);

    }

	private void addDetailDeleteButton() {
        TableColumn<OrderItem, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<OrderItem, Void>, TableCell<OrderItem, Void>> cellFactory = new Callback<TableColumn<OrderItem, Void>, TableCell<OrderItem, Void>>() {
            @Override
            public TableCell<OrderItem, Void> call(final TableColumn<OrderItem, Void> param) {
                final TableCell<OrderItem, Void> cell = new TableCell<OrderItem, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderItem item = getTableView().getItems().get(getIndex());
                            String res = orderItemController.deleteOrderItem(item);
                            System.out.println(res);

                        	handleOrder();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        detailTable.getColumns().add(colBtn);

    }
	
	public void menu(Main main) {
		orderController.getSession(main.getSession());
		System.out.println("Chef Page");
		menu = new Menu("Menu");
		m1 = new MenuItem("Handle Order");
		m2 = new MenuItem("Logout");
		mb = new MenuBar();
		mainBox = new VBox();
		container1 = new VBox();
		container2 = new VBox();
		bp = new BorderPane();
		gpOrder = new GridPane();
		gpForm = new GridPane();
		title = new Label("Handle Orders");
		orderTitle = new Label("Order Table");
		detailTitle = new Label("Order Details Table");
		formTitle = new Label("Update Quantity");
		itemLabel = new Label("Menu Item Name");
		qtyLabel = new Label("Quantity");
		warning = new Label();
		itemField = new Label("-");
		qtySpinner = new Spinner<>(1, 100, 1);
		updateBtn = new Button("Update");
		spOrder = new ScrollPane();
		spDetail = new ScrollPane();
		
		orderTable = new TableView<>();
		orderId = new TableColumn<>("Order ID");
		orderName = new TableColumn<>("Order Name");
		orderStatus = new TableColumn<>("Status");
		orderUserId = new TableColumn<>("User ID");
		orderTotal = new TableColumn<>("Order Total");
		
		detailTable = new TableView<>();
		detailOrderId = new TableColumn<>("Order ID");
		detailItemName = new TableColumn<>("Item Name");
		detailQuantity = new TableColumn<>("Quantity");
		detailId = new TableColumn<>("Detail ID");
		detailItemId = new TableColumn<>("Item ID");
		detailItemPrice = new TableColumn<>("Price/item");
		
		qtySpinner.getValueFactory().setWrapAround(true);
		
		menu.getItems().addAll(m1, new SeparatorMenuItem(), m2);
		mb.getMenus().add(menu);
		
		orderTable.getColumns().addAll(orderId, orderName, orderStatus, orderTotal);
		orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
		orderUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
		orderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		orderId.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderName.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderTotal.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderStatus.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		
		detailTable.getColumns().addAll(detailId, detailOrderId, detailItemName, detailQuantity, detailItemPrice);
		detailId.setCellValueFactory(new PropertyValueFactory<>("id"));
		detailOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		detailItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
		detailItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		detailQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		detailItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
	
		detailOrderId.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		detailItemName.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		detailQuantity.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		
		mainBox.getChildren().addAll(title, gpOrder);
		gpOrder.add(container1, 0, 0);
		gpOrder.add(container2, 1, 0);
		gpForm.add(itemLabel, 0, 0);
		gpForm.add(itemField, 1, 0);
		gpForm.add(qtyLabel, 0, 1);
		gpForm.add(qtySpinner, 1, 1);
		container1.getChildren().addAll(orderTitle, spOrder);
		container2.getChildren().addAll(detailTitle, spDetail, formTitle, gpForm, warning, updateBtn);
		bp.setTop(mb);
		bp.setCenter(mainBox);
		setCenter(bp);
		
		mainBox.setAlignment(Pos.TOP_CENTER);
		orderTable.setPrefSize(475, 500);
		detailTable.setPrefSize(475, 300);
		gpOrder.setAlignment(Pos.TOP_CENTER);
		gpOrder.setMinSize(1000, 500);
		gpOrder.setHgap(50);
		gpForm.setMinSize(450, 50);
		gpForm.setHgap(30);
		gpForm.setVgap(10);
		title.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		orderTitle.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 18));
		detailTitle.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 18));
		formTitle.setFont(Font.font("Calibri", FontWeight.MEDIUM, FontPosture.REGULAR, 16));
		title.setMinHeight(80);
		VBox.setMargin(orderTitle, new Insets(0, 0, 10, 10));
		VBox.setMargin(detailTitle, new Insets(0, 0, 10, 0));
		VBox.setMargin(formTitle, new Insets(30, 0, 10, 0));
		qtySpinner.setPrefWidth(100);
		VBox.setMargin(warning, new Insets(7, 0, 5, 0));
		updateBtn.setPrefWidth(70);
		addOrderHandleButton();
		addOrderDeleteButton();
		addDetailDeleteButton();
	}
	
	public void handleOrder() {
		gpForm.getChildren().clear();
		gpForm.add(itemLabel, 0, 0);
		gpForm.add(itemField, 1, 0);
		gpForm.add(qtyLabel, 0, 1);
		gpForm.add(qtySpinner, 1, 1);
		
		container1.getChildren().clear();
		container1.getChildren().addAll(orderTitle, spOrder);
		container2.getChildren().clear();
		container2.getChildren().addAll(detailTitle, spDetail, formTitle, gpForm, warning, updateBtn);
		
		spOrder.setContent(orderTable);
		spDetail.setContent(detailTable);
		
		ArrayList<Order> orders = orderController.getAllOrders();
		
		orderTable.getItems().clear();
		detailTable.getItems().clear();
		
		for (Order o : orders) {
			orderTable.getItems().add(new Order(o.getId(), 
					o.getUserId(), o.getName(), o.getStatus(), 
					o.getTotal()));
		}
		
		orderTable.setOnMouseClicked(e -> {
			detailTable.getItems().clear();
			Order order = (Order) orderTable.getSelectionModel().getSelectedItem();
			ArrayList<OrderItem> details = orderItemController.getAllOrderItemsByOrderId(order.getId());
			for (OrderItem d : details) {
				detailTable.getItems().add(new OrderItem(d.getId(), 
						d.getOrderId(), d.getItemId(), d.getItemName(), d.getQuantity(), d.getPrice()));
			}
			detailTable.setOnMouseClicked(e1 -> {
				OrderItem item = (OrderItem) detailTable.getSelectionModel().getSelectedItem();
				itemField.setText(item.getItemName());
				qtySpinner.getValueFactory().setValue(Integer.parseInt(item.getQuantity()));
				
				updateBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						String res = orderItemController.updateOrderItem(item.getId(), Integer.toString(qtySpinner.getValue()), details, item.getOrderId());
						System.out.println(res);
						
						handleOrder();
					}
				});
			});
		});
		
	}
	
	public ChefPage(Stage primaryStage, Main main) {
		// TODO Auto-generated constructor stub
		menu(main);
		handleOrder();
		
		m1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleOrder();
			}
		});
		
		m2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				main.setSession(null);
				primaryStage.setScene(new Scene(new Register(primaryStage, main), 1200, 700));
			}
		});
	}

}

package view;

import java.util.ArrayList;

import controller.OrderController;
import controller.OrderItemController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main;
import model.Order;
import model.OrderItem;

public class WaiterPage extends BorderPane {
	
	OrderController orderController = new OrderController();
	OrderItemController orderItemController = new OrderItemController();
	
	Menu menu;
	MenuBar mb;
	MenuItem m1, m2; 
	VBox mainbox, orderContainer, detailContainer;
	BorderPane bp;
	GridPane gpOrder, gpForm;
	Label orderTitle, detailTitle, mainTitle, formTitle;
	Label warning, itemLabel, itemQuan, itemField, itemSubTotalLbl, itemSubTotal;
	Spinner<Integer> itemQuantSpin;
	Button updateBtn;
	TableView orderTable, detailTable;
	TableColumn orderId, orderName, orderStatus, orderTotal;
	TableColumn detailOrderId, detailItemName, detailQuantity, detailId, detailItemId, detailItemPrice;
	ScrollPane spOrder, spDetail;

    private void orderActionColumn() {
        TableColumn<Order, Void> orderAct = new TableColumn("Action");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                	
                	private final HBox actions;
                	
                	{
                		Button delbtn = new Button("Delete");
                		Button changebtn = new Button("Add New");
                		Button servebtn = new Button("Serve");
                		
                		delbtn.setPrefWidth(75);
                		servebtn.setPrefWidth(75);
                		changebtn.setPrefWidth(75);
                		
                		//button to delete order
                        delbtn.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            String act = orderController.deleteOrder(order.getId());
                            System.out.println(act);
                            serve();
                        });
                        
                        //button to change the content of the order
						changebtn.setOnAction((ActionEvent event) -> {
//                          Order order = getTableView().getItems().get(getIndex());
//                          String act = orderController.serveOrder(order.getId());
//                          System.out.println(act);

//                          serve();
						});
						
						//button to change the status of the order to served
                        servebtn.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            String act = orderController.serveOrder(order.getId());
                            System.out.println(act);
                            
                            serve();
                        });
                		
                		actions = new HBox(5, servebtn, changebtn, delbtn);
                		actions.setAlignment(Pos.CENTER);
                		
                	}

                	@Override
                	public void updateItem(Void item, boolean empty) {
                		super.updateItem(item, empty);
                		if (empty) {
                			setGraphic(null);
                		} else {
                			setGraphic(empty ? null : actions);
                		}
                	}
                    
                };
                return cell;
            }
        };

        orderAct.setCellFactory(cellFactory);

        orderTable.getColumns().add(orderAct);

    }

    private void detailDeleteButton() {
        TableColumn<OrderItem, Void> detailDel = new TableColumn("Delete Item");

        Callback<TableColumn<OrderItem, Void>, TableCell<OrderItem, Void>> cellFactory = new Callback<TableColumn<OrderItem, Void>, TableCell<OrderItem, Void>>() {
            @Override
            public TableCell<OrderItem, Void> call(final TableColumn<OrderItem, Void> param) {
                final TableCell<OrderItem, Void> cell = new TableCell<OrderItem, Void>() {

                    private final Button detailDelBtn = new Button("Delete");

                    {
                    	//button to delete the menu item in the selected order
                    	detailDelBtn.setOnAction((ActionEvent event) -> {
                            OrderItem order = getTableView().getItems().get(getIndex());
                            String act = orderItemController.deleteOrderItem(order);
                            serve();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(detailDelBtn);
                        }
                    }
                };
                return cell;
            }
        };

        detailDel.setCellFactory(cellFactory);

        detailTable.getColumns().add(detailDel);

    }
	
	public void menuInit(Main main) {
		orderController.getSession(main.getSession());
		
		//menubar init
		menu = new Menu("Menu");
		m1 = new MenuItem("Serve Order");
		m2 = new MenuItem("Log Out");
		mb = new MenuBar();
		
		//Container init
		mainbox = new VBox();
		orderContainer = new VBox();
		detailContainer = new VBox();
		bp = new BorderPane();
		gpOrder = new GridPane();
		gpForm = new GridPane();
		spOrder = new ScrollPane();
		spDetail = new ScrollPane();

		//Title label init
		orderTitle = new Label("Orders");
		detailTitle = new Label("Order Details");
		mainTitle = new Label("Serve Orders");
		formTitle = new Label("Serving Details");
		
		//form label init 
		warning = new Label();
		itemLabel = new Label("Menu Item Name");
		itemQuan = new Label("Quantity");
		itemField = new Label("");
		itemQuantSpin = new Spinner<>(1, 100, 1);
		itemQuantSpin.setEditable(true);
		itemQuantSpin.getValueFactory().setWrapAround(true);
		itemSubTotalLbl = new Label("Sub Total");
		itemSubTotal = new Label("");
		
		//order header table init
		orderTable = new TableView();
		orderId = new TableColumn("Order ID");
		orderName = new TableColumn("Order Name");
		orderStatus = new TableColumn("Status");
		orderTotal = new TableColumn("Total");
		
		//order detail table init 
		detailTable = new TableView();
		detailId = new TableColumn("Detail ID");
		detailOrderId = new TableColumn("Order ID");
		detailItemId = new TableColumn("Item ID");
		detailItemName = new TableColumn("Item Name");
		detailQuantity = new TableColumn("Quantity");
		detailItemPrice = new TableColumn("Price/Item");
		
		updateBtn = new Button("Update");
		menuSetup();
		orderActionColumn();
		detailDeleteButton();
	}
	
	public void menuSetup() {
		//orderheadertable setup
		orderTable.getColumns().addAll(orderId, orderName, orderStatus, orderTotal);
		orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
		orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
		orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		orderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		orderId.setStyle("-fx-alignment: CENTER");
		orderName.setStyle("-fx-alignment: CENTER");
		orderStatus.setStyle("-fx-alignment: CENTER");
		orderTotal.setStyle("-fx-alignment: CENTER");
		
		orderId.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderName.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderStatus.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		orderTotal.prefWidthProperty().bind(orderTable.widthProperty().divide(6));
		
		spOrder.setContent(orderTable);
		
		//orderdetailtable setup
		detailTable.getColumns().addAll(detailId, detailOrderId, 
				detailItemId, detailItemName, detailQuantity, detailItemPrice);
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
		
		detailOrderId.prefWidthProperty().bind(detailTable.widthProperty().divide(6));
		detailItemName.prefWidthProperty().bind(detailTable.widthProperty().divide(6));
		detailQuantity.prefWidthProperty().bind(detailTable.widthProperty().divide(6));
		
		spDetail.setContent(detailTable);
		
		//menubar setup
		menu.getItems().addAll(m1,new SeparatorMenuItem(), m2);
		mb.getMenus().add(menu);
		
		//container setup
		gpOrder.add(orderContainer, 0, 0);
		gpOrder.add(detailContainer, 1, 0);
		
		//alternative of using gpOrder : scroll pane
		//container(VBox) can only be added in 1 pane
		gpForm.add(itemLabel, 0, 0);
		gpForm.add(itemField, 1, 0);
		gpForm.add(itemQuan, 0, 1);
		gpForm.add(itemQuantSpin, 1, 1);
		gpForm.add(itemSubTotalLbl, 0, 2);
		gpForm.add(itemSubTotal, 1, 2);
		
		orderContainer.getChildren().addAll(orderTitle, orderTable);
		detailContainer.getChildren().addAll(detailTitle, detailTable, 
				formTitle, gpForm, warning, updateBtn);
		mainbox.getChildren().addAll(mainTitle, gpOrder);
		
		bp.setTop(mb);
		bp.setCenter(mainbox);
		setCenter(bp);
		
		//title setup
		mainTitle.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 26));
		orderTitle.setFont(Font.font("Calibri", FontWeight.BLACK, FontPosture.REGULAR, 19));
		detailTitle.setFont(Font.font("Calibri", FontWeight.BLACK, FontPosture.REGULAR, 19));
		formTitle.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 17));
		mainTitle.setMinHeight(80);
		
		//title layout
		VBox.setMargin(orderTitle, new Insets(0, 0, 10, 10));
		VBox.setMargin(detailTitle, new Insets(0, 0, 10, 0));
		VBox.setMargin(formTitle, new Insets(30, 0, 10, 0));
		VBox.setMargin(warning, new Insets(7, 0, 5, 0));
		
		//element setup
		itemQuantSpin.setPrefWidth(100);
		updateBtn.setPrefWidth(70);
		
		//pane layout
		mainbox.setAlignment(Pos.TOP_CENTER);
		orderTable.setPrefSize(480, 500);
		detailTable.setPrefSize(480, 300);
		
		gpOrder.setAlignment(Pos.CENTER);
		gpOrder.setMinSize(1000, 500);
		gpOrder.setHgap(50);
		gpForm.setMinSize(460, 50);
		gpForm.setHgap(30);
		gpForm.setVgap(10);
	}

	public void serve() {
		
		itemField.setText("");
		itemQuantSpin.getValueFactory().setValue(1);
		itemSubTotal.setText("");
		warning.setText("");
		
		ArrayList<Order> orders = orderController.getAllOrders();

		orderTable.getItems().clear();
		detailTable.getItems().clear();
		
		for(Order o : orders) {
			orderTable.getItems().add(new Order(o.getId(), 
					o.getUserId(), o.getName(), o.getStatus(), 
					o.getTotal()));
		}
		
		orderTable.setOnMouseClicked(e->{
			detailTable.getItems().clear();
			Order currorder = (Order)orderTable.getSelectionModel().getSelectedItem();
			
			ArrayList<OrderItem> details = orderItemController.getAllOrderItemsByOrderId(currorder.getId());
			
			for(OrderItem d : details) {
				detailTable.getItems().add(new OrderItem(d.getId(), d.getOrderId(), d.getItemId(),
						d.getItemName(), d.getQuantity(), d.getPrice()));
			}
			
			detailTable.setOnMouseClicked(e1->{
				OrderItem item = (OrderItem)detailTable.getSelectionModel().getSelectedItem();
				
				itemField.setText(item.getItemName());
				itemQuantSpin.getValueFactory().setValue(item.getQuantity());
				itemQuantSpin.valueProperty().addListener(new ChangeListener<Integer>() {

					@Override
					public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
						int currentVal = itemQuantSpin.getValue();
						
						itemSubTotal.setText(Integer.toString(item.getPrice() * currentVal));
					}
					
				});
				
				updateBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						String updt = orderItemController.updateOrderItem(item.getId(),
								Integer.toString(itemQuantSpin.getValue()), details, item.getOrderId());
						System.out.println(updt);
						serve();				
					}
				});
			});
		});
	}
	
	public WaiterPage(Stage primaryStage, Main main) {
		menuInit(main);
		serve();
		
		m1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				serve();
			}
		});
		
		m2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				main.setSession(null);
				primaryStage.setScene(new Scene(new Register(primaryStage, main), 1200, 700));
			}
		});
	}

}

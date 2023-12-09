package view;

import java.util.ArrayList;

import controller.MenuItemController;
import controller.OrderController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main;
import model.*;

public class CustomerPage extends BorderPane {
	ArrayList<Cart> cart = new ArrayList<Cart>();
	MenuItemController menuItemController = new MenuItemController();
	OrderController orderController = new OrderController();
	Integer total = 0;

	Menu menu;
	MenuBar mb;
	MenuItem m1, m2;
	VBox container, container2, main;
	BorderPane bp;
	GridPane gp, gp1;
	Label title, totalLabel;
	TableView itemTable, cartTable;
	TableColumn colItemId, colItemName, colItemDesc, colItemPrice, colItemAction;
	TableColumn colCartName, colCartDesc, colCartSubTotal, colCartQuantity, colCartAction;
	ScrollPane sp, sp2;
	Button btnCheckOut;
	
	private void calculateTotalOrder() {
		total = 0;
		for (Cart i : cart) {
			total += i.getSubTotal();
		}
	}
	
	private void refreshCartTable() {
		cartTable.getItems().clear();
		for (Cart i : cart) {
			cartTable.getItems().add(new model.Cart(i.getNameView(), i.getDescriptionView(), i.getSubTotal(), i.getQuantity(), i.getMenuItem()));
		}
		calculateTotalOrder();
		totalLabel.setText("Total: Rp." + total);
	}
	
	private Cart findCartByName(String name) {
		for (Cart cart : cart) {
			if (cart.getMenuItem().getName().equals(name)) return cart;
		}
		return null;
	}

	private void actionCartButton() {
		TableColumn<model.Cart, Void> colBtn = new TableColumn("Action");

		Callback<TableColumn<model.Cart, Void>, TableCell<model.Cart, Void>> cellFactory = new Callback<TableColumn<model.Cart, Void>, TableCell<model.Cart, Void>>() {
			@Override
			public TableCell<model.Cart, Void> call(final TableColumn<model.Cart, Void> param) {
				final TableCell<model.Cart, Void> cell = new TableCell<model.Cart, Void>() {

					private final Button btnSub = new Button("-");
					private final Button btnAdd = new Button("+");

					{
						btnSub.setOnAction((ActionEvent event) -> {
							model.Cart i = getTableView().getItems().get(getIndex());
							
							for (Cart c : cart) {
								if (c.getMenuItem().getName().equals(i.getMenuItem().getName())) {
									c.setQuantity(c.getQuantity() - 1);
									if (c.getQuantity() <= 0) {
										cart.remove(c);
									} else {
										c.setSubTotal(c.getSubTotal() - Integer.parseInt(c.getMenuItem().getPrice()));
									}
									refreshCartTable();
									break;
								}
							}
						});
						
						btnAdd.setOnAction((ActionEvent event) -> {
							model.Cart i = getTableView().getItems().get(getIndex());
							
							for (Cart c : cart) {
								if (c.getMenuItem().getName().equals(i.getMenuItem().getName())) {
									c.setQuantity(c.getQuantity() + 1);
									c.setSubTotal(c.getSubTotal() + Integer.parseInt(c.getMenuItem().getPrice()));
									refreshCartTable();
									break;
								}
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox pane = new HBox(btnSub, btnAdd);
							setGraphic(pane);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		cartTable.getColumns().add(colBtn);
	}
	
	private void addItemToCartButton() {
		TableColumn<model.MenuItem, Void> colBtn = new TableColumn("Action");

		Callback<TableColumn<model.MenuItem, Void>, TableCell<model.MenuItem, Void>> cellFactory = new Callback<TableColumn<model.MenuItem, Void>, TableCell<model.MenuItem, Void>>() {
			@Override
			public TableCell<model.MenuItem, Void> call(final TableColumn<model.MenuItem, Void> param) {
				final TableCell<model.MenuItem, Void> cell = new TableCell<model.MenuItem, Void>() {

					private final Button btn = new Button("Add to Cart");

					{
						btn.setOnAction((ActionEvent event) -> {
							model.MenuItem i = getTableView().getItems().get(getIndex());
							Cart findCart = findCartByName(i.getName()); 
							
							if (findCart != null) {
								for (Cart c : cart) {
									if (c.getMenuItem().getName().equals(i.getName())) {
										c.setQuantity(c.getQuantity() + 1);				
										c.setSubTotal(c.getSubTotal() + Integer.parseInt(c.getMenuItem().getPrice()));
										refreshCartTable();
										break;
									}
								}
							} else {
								Cart addCart = new Cart(i.getName(), i.getDescription(), Integer.parseInt(i.getPrice()), 1, i);
								cart.add(addCart);	
								refreshCartTable();
							}
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

		itemTable.getColumns().add(colBtn);
	}

	private void menu() {
		System.out.println("Customer page");
		menu = new Menu("Menu");
		m1 = new MenuItem("Order Menu");
		m2 = new MenuItem("Logout");
		mb = new MenuBar();
		container = new VBox();
		container2 = new VBox();
		main = new VBox();
		title = new Label();
		totalLabel = new Label();
		btnCheckOut = new Button("Checkout Order");

		itemTable = new TableView<>();
		colItemId = new TableColumn<>("Item ID");
		colItemName = new TableColumn<>("Name");
		colItemDesc = new TableColumn<>("Description");
		colItemPrice = new TableColumn<>("Price");
		colItemAction = new TableColumn<>("Add to Cart");
		
		cartTable = new TableView<>();
		colCartName = new TableColumn<>("Name");
		colCartDesc = new TableColumn<>("Description");
		colCartSubTotal = new TableColumn<>("Subtotal");
		colCartQuantity = new TableColumn<>("Quantity");
		colCartAction = new TableColumn<>("Action");

		sp = new ScrollPane();
		sp2 = new ScrollPane();
		
		bp = new BorderPane();
		gp = new GridPane();
		gp1 = new GridPane();

		itemTable.getColumns().addAll(colItemId, colItemName, colItemDesc, colItemPrice);
		colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
		colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colItemDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
		colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		addItemToCartButton();
		
		cartTable.getColumns().addAll(colCartName, colCartDesc, colCartSubTotal, colCartQuantity);
		colCartName.setCellValueFactory(new PropertyValueFactory<>("nameView"));
		colCartDesc.setCellValueFactory(new PropertyValueFactory<>("descriptionView"));
		colCartSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
		colCartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		actionCartButton();
		
		colItemId.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemName.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemDesc.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemPrice.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
			
		colCartName.prefWidthProperty().bind(cartTable.widthProperty().divide(5));
		colCartDesc.prefWidthProperty().bind(cartTable.widthProperty().divide(5));
		colCartSubTotal.prefWidthProperty().bind(cartTable.widthProperty().divide(5));
		colCartQuantity.prefWidthProperty().bind(cartTable.widthProperty().divide(5));
		
		menu.getItems().addAll(m1, new SeparatorMenuItem(), m2);
		mb.getMenus().add(menu);
		container.getChildren().addAll(sp);
		container2.getChildren().addAll(sp2);
		container.setAlignment(Pos.TOP_CENTER);
		container2.setAlignment(Pos.TOP_CENTER);
		gp.add(container, 0, 0);
		gp.add(container2, 1, 0);
		gp1.add(totalLabel, 0, 0);
		gp1.add(btnCheckOut, 1, 0);
		main.getChildren().addAll(title, gp);
		bp.setTop(mb);
		bp.setCenter(main);
		bp.setBottom(gp1);
		setCenter(bp);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		gp.setMinSize(1000, 500);
		gp.setAlignment(Pos.TOP_CENTER);
		gp.setHgap(50);
		gp1.setMinSize(1000, 500);
		gp1.setAlignment(Pos.TOP_CENTER);
		gp1.setHgap(50);
		title.setMinHeight(100);
		title.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		totalLabel.setPrefHeight(100);
		totalLabel.setAlignment(Pos.TOP_CENTER);
		totalLabel.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		main.setAlignment(Pos.TOP_CENTER);
		itemTable.setMinWidth(430);
		cartTable.setMinWidth(430);
		btnCheckOut.setAlignment(Pos.TOP_CENTER);
		btnCheckOut.setPrefHeight(100);
	}

	private void manageMenu() {
		title.setText("Order Page");

		sp.setContent(itemTable);
		sp2.setContent(cartTable);

		ArrayList<model.MenuItem> menus = menuItemController.getAllMenuItem();

		itemTable.getItems().clear();
		for (model.MenuItem m : menus) {
			itemTable.getItems().add(new model.MenuItem(m.getItemId(), m.getName(), m.getDescription(), m.getPrice()));
		}
		
		refreshCartTable();
	}

	public CustomerPage(Stage primaryStage, Main main) {
		primaryStage.setTitle("Customer Page");

		menu();
		manageMenu();

		m1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				manageMenu();
			}
		});

		m2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				main.setSession(null);
				primaryStage.setScene(new Scene(new Register(primaryStage, main), 1200, 700));
			}
		});
		
		btnCheckOut.setOnMouseClicked(e -> {
			String msg = orderController.createOrder("2", cart, total);
			cart.removeAll(cart);
			refreshCartTable();
		});

	}
}

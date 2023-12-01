package view;

import java.util.ArrayList;

import controller.AdminController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main;
import model.User;

public class AdminPage extends BorderPane {
	AdminController adminController = new AdminController();
	
	Menu menu;
	MenuBar mb;
	MenuItem m1, m2, m3;
	VBox container, container2, main;
	BorderPane bp;
	GridPane gp, gpUpdate, gpCreate;
	Label title, title2, updateTitle, createTitle, roleLabel, itemId, itemName, itemDesc, itemPrice, idField;
	Label updateItemId, updateItemName, updateItemDesc, updateItemPrice, updateIdField;
	TextArea updateNameField, updateDescField, updatePriceField;
	TextArea nameField, descField, priceField;
	TableView table, itemTable;
	TableColumn id, uname, email, pass, role, action;
	TableColumn colItemId, colItemName, colItemDesc, colItemPrice, colItemAction;
	ScrollPane sp;
	ComboBox<String> roleComboBox;
	Button editBtn, deleteBtn;
	Button itemEditBtn, itemCreateBtn;
	
	private void addUserDeleteButton() {
        TableColumn<User, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            String res = adminController.deleteUser(user.getId());
                            System.out.println(res);
                            
                            manageUser();
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

        table.getColumns().add(colBtn);

    }
	
	private void addItemDeleteButton() {
        TableColumn<model.MenuItem, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<model.MenuItem, Void>, TableCell<model.MenuItem, Void>> cellFactory = new Callback<TableColumn<model.MenuItem, Void>, TableCell<model.MenuItem, Void>>() {
            @Override
            public TableCell<model.MenuItem, Void> call(final TableColumn<model.MenuItem, Void> param) {
                final TableCell<model.MenuItem, Void> cell = new TableCell<model.MenuItem, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            model.MenuItem item = getTableView().getItems().get(getIndex());
//                            String res = adminController.deleteUser(item.getId());
//                            System.out.println(res);
                            System.out.println("hehe");
                            
                            manageMenu();
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
		System.out.println("Admin page");
		menu = new Menu("Menu");
		m1 = new MenuItem("Manage Users");
		m2 = new MenuItem("Manage Menu Items");
		m3 = new MenuItem("Logout");
		mb = new MenuBar();
		container = new VBox();
		container2 = new VBox();
		main = new VBox();
		table = new TableView<>();
		id = new TableColumn<>("User ID");
		uname = new TableColumn<>("Username");
		email = new TableColumn<>("Email");
		pass = new TableColumn<>("Password");
		role = new TableColumn<>("Role");
		action = new TableColumn<>("Delete");
		
		itemTable = new TableView<>();
		colItemId = new TableColumn<>("Item ID");
		colItemName = new TableColumn<>("Name");
		colItemDesc = new TableColumn<>("Description");
		colItemPrice = new TableColumn<>("Price");
		colItemAction = new TableColumn<>("Delete");
		
		sp = new ScrollPane();
		roleComboBox = new ComboBox<>();
		editBtn = new Button("Edit");
		itemEditBtn = new Button("Update");
		itemCreateBtn = new Button("Add");
		nameField = new TextArea();
		descField = new TextArea();
		priceField = new TextArea();
		updateDescField = new TextArea();
		updateNameField = new TextArea();
		updatePriceField = new TextArea();
		
		bp = new BorderPane();
		title = new Label("Manage User");
		title2 = new Label("User Data");
		updateTitle = new Label("Update Data");
		createTitle = new Label("Create Data");
		roleLabel = new Label("Role : ");
		itemId = new Label("Item ID");
		itemName = new Label("Item Name");
		itemDesc = new Label("Item Description");
		itemPrice = new Label("Item Price");
		idField = new Label();
		updateItemId = new Label("Item ID");
		updateItemName = new Label("Item Name");
		updateItemDesc = new Label("Item Description");
		updateItemPrice = new Label("Item Price");
		updateIdField = new Label();
		bp = new BorderPane();
		gp = new GridPane();
		gpUpdate = new GridPane();
		gpCreate = new GridPane();
		
		table.getColumns().addAll(id, uname, email, pass, role);
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		uname.setCellValueFactory(new PropertyValueFactory<>("username"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		pass.setCellValueFactory(new PropertyValueFactory<>("password"));
		role.setCellValueFactory(new PropertyValueFactory<>("role"));
		addUserDeleteButton();
		
		id.prefWidthProperty().bind(table.widthProperty().divide(6));
		uname.prefWidthProperty().bind(table.widthProperty().divide(6));
		email.prefWidthProperty().bind(table.widthProperty().divide(6));
		pass.prefWidthProperty().bind(table.widthProperty().divide(6));
		role.prefWidthProperty().bind(table.widthProperty().divide(6));
		action.prefWidthProperty().bind(table.widthProperty().divide(6));
		
		itemTable.getColumns().addAll(colItemId, colItemName, colItemDesc, colItemPrice);
		colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
		colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colItemDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
		colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		addItemDeleteButton();
		
		colItemId.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemName.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemDesc.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		colItemPrice.prefWidthProperty().bind(itemTable.widthProperty().divide(5));
		
		menu.getItems().addAll(m1, m2, new SeparatorMenuItem(), m3);
		mb.getMenus().add(menu);
		container.getChildren().addAll(sp);
		//KALO MAU INI EDIT GPP BUAT NENTUIN TABLENYA DI CENTER VERTICAL HORIZONTAL APA TOP DOANG
		container.setAlignment(Pos.TOP_CENTER);
		gp.add(container, 0, 0);
		gp.add(container2, 1, 0);
		main.getChildren().addAll(title, gp);
		bp.setTop(mb);
		bp.setCenter(main);
		setCenter(bp);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		roleComboBox.getItems().addAll("Admin", "Chef", "Waiter", "Customer", "Cashier");

		gp.setMinSize(1000, 500);
		gp.setAlignment(Pos.TOP_CENTER);
		gp.setHgap(50);
		gpUpdate.setMinSize(400, 30);
		gpUpdate.setHgap(50);
		gpUpdate.setVgap(10);
		gpCreate.setMinSize(400, 100);
		gpCreate.setHgap(50);
		gpCreate.setVgap(10);
		editBtn.setMinWidth(70);
		VBox.setMargin(title2, new Insets(10, 0, 10, 0));
		VBox.setMargin(updateTitle, new Insets(5, 0, 5, 0));
		VBox.setMargin(createTitle, new Insets(30, 0, 5, 0));
		title.setMinHeight(100);
		title.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 25));
		title2.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
		main.setAlignment(Pos.TOP_CENTER);
		table.setMinWidth(430);
		itemTable.setMinWidth(430);
		GridPane.setMargin(gpUpdate, new Insets(0, 0, 15, 0));
		GridPane.setMargin(gpCreate, new Insets(0, 0, 15, 0));
		itemEditBtn.setMinWidth(70);
		itemCreateBtn.setMinWidth(70);
		
	}
	
	private void manageUser() {
		
		gpUpdate.getChildren().clear();
		gpUpdate.add(roleLabel, 0, 0);
		gpUpdate.add(roleComboBox, 1, 0);
		
		container2.getChildren().clear();
		container2.getChildren().addAll(title2, gpUpdate, editBtn);
		
		title.setText("Manage User");
		title2.setText("User Data");
		
		sp.setContent(table);
		
		ArrayList<User> res = adminController.getUserList();
		
		table.getItems().clear();
		for (User u : res) {
			table.getItems().add(new User(u.getId(), u.getUsername(), u.getEmail(), u.getPassword(), u.getRole()));
		}
		
		table.setOnMouseClicked(e -> {
			User user = (User) table.getSelectionModel().getSelectedItem();
			roleComboBox.getSelectionModel().select(user.getRole());
			
			editBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String status = adminController.editRole(roleComboBox.getValue(), user.getId());
					System.out.println(status);
					manageUser();
				}
			});
		});
		
	}
	
	private void manageMenu() {
		title.setText("Manage Menu Items");
		title2.setText("Item Data");
		
		gpUpdate.getChildren().clear();
		gpCreate.getChildren().clear();
		
		sp.setContent(itemTable);
		
		updateIdField.setText("-");
		gpUpdate.add(updateItemId, 0, 0);
		gpUpdate.add(updateIdField, 1, 0);
		gpUpdate.add(updateItemName, 0, 1);
		gpUpdate.add(updateNameField, 1, 1);
		gpUpdate.add(updateItemDesc, 0, 2);
		gpUpdate.add(updateDescField, 1, 2);
		gpUpdate.add(updateItemPrice, 0, 3);
		gpUpdate.add(updatePriceField, 1, 3);
		
		idField.setText("id");
		gpCreate.add(itemName, 0, 0);
		gpCreate.add(nameField, 1, 0);
		gpCreate.add(itemDesc, 0, 1);
		gpCreate.add(descField, 1, 1);
		gpCreate.add(itemPrice, 0, 2);
		gpCreate.add(priceField, 1, 2);
		
		updateTitle.setFont(Font.font("Calibri", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
		createTitle.setFont(Font.font("Calibri", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
		
		container2.getChildren().clear();
		container2.getChildren().addAll(title2, updateTitle, gpUpdate, itemEditBtn, createTitle, gpCreate, itemCreateBtn);
		
		updateNameField.setPrefSize(300, 10);
		updateDescField.setPrefSize(300, 10);
		updatePriceField.setPrefSize(300, 10);
		
		nameField.setPrefSize(300, 10);
		descField.setPrefSize(300, 10);
		priceField.setPrefSize(300, 10);
		
		updateNameField.setText("");
		updateDescField.setText("");
		updatePriceField.setText("");
		
		nameField.setText("");
		descField.setText("");
		priceField.setText("");
		
		ArrayList<model.MenuItem> menus = adminController.getMenuItemList();
		
		itemTable.getItems().clear();
		for (model.MenuItem m : menus) {
			itemTable.getItems().add(new model.MenuItem(m.getItemId(), m.getName(), m.getDescription(), m.getPrice()));
		}
		
		itemCreateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String status = adminController.createMenuItem(nameField.getText(), descField.getText(), priceField.getText());
				System.out.println(status);

				manageMenu();
			}
		});
		
		itemTable.setOnMouseClicked(e -> {
			model.MenuItem item = (model.MenuItem) itemTable.getSelectionModel().getSelectedItem();
			updateIdField.setText(item.getItemId());
			updateNameField.setText(item.getName());
			updateDescField.setText(item.getDescription());
			updatePriceField.setText(item.getPrice());
			
			itemEditBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String status = adminController.updateMenuItem(item.getItemId(), 
							updateNameField.getText(), updateDescField.getText(), updatePriceField.getText());
					System.out.println(status);

					manageMenu();
				}
			});
		});
	}
	
	public AdminPage(Stage primaryStage, Main main) {
		primaryStage.setTitle("Admin Page");
		
		menu();
		manageUser();
		
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

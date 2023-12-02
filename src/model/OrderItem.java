package model;

public class OrderItem {
	private String id;
	private String orderId;
	private String itemId;
	private String itemName;
	private String quantity;
	private String price;
	
	public OrderItem(String id, String orderId, String itemId, String itemName, String quantity, String price) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}

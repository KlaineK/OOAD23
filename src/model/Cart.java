package model;

public class Cart {
	private String nameView;
	private String descriptionView;
	private MenuItem menuItem;
	private Integer subTotal;
	private Integer quantity;
	
	public Cart(String nameView, String descriptionView, Integer subTotal, Integer quantity, MenuItem menuItem) {
		super();
		this.nameView = nameView;
		this.descriptionView = descriptionView;
		this.subTotal = subTotal;
		this.quantity = quantity;
		this.menuItem = menuItem;
	}
	
	public String getNameView() {
		return nameView;
	}

	public void setNameView(String nameView) {
		this.nameView = nameView;
	}

	public String getDescriptionView() {
		return descriptionView;
	}

	public void setDescriptionView(String descriptionView) {
		this.descriptionView = descriptionView;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public Integer getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}

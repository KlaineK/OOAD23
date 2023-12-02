package model;

public class Order {
	private String id;
	private String userId;
	private String name;
	private String status;
	private String total;
	
	public Order(String id, String userId, String name, String status, String total) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.status = status;
		this.total = total;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}

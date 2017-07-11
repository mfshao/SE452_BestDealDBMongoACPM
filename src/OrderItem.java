import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/OrderItem")

public class OrderItem extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String itemID;
	private int orderID;
	private String type;
	private String name;
	private double price;
	private String image;
	private String manufacturer;
	private double discount;
	private int amount;
	private double extraCost;

	public OrderItem(String itemID, int orderID, String type, String name, double price, String image, String manufacturer, double discount, int amount,
			double extraCost) {
		this.itemID = itemID;
		this.orderID = orderID;
		this.type = type;
		this.name = name;
		this.price = price;
		this.image = image;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.amount = amount;
		this.extraCost = extraCost;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public double getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(double extraCost) {
		this.extraCost = extraCost;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}


public abstract class Product {
	private String id;
	private String name;
	private double price;
	private String image;
	private String manufacturer;
	private String condition;
	private double discount;
	private String category;

	public Product(String id, String name, double price, String image, String manufacturer, String condition, double discount) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.condition = condition;
		this.discount = discount;
		this.manufacturer = manufacturer;
	}

	public Product() {
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}

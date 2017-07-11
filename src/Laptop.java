import java.util.HashMap;

public class Laptop extends Product {

	HashMap<String, String> accessories;

	public Laptop(String id, String name, double price, String image, String manufacturer, String condition,
			double discount) {
		super(id, name, price, image, manufacturer, condition, discount);
		this.setCategory("laptop");
		this.accessories = new HashMap<String, String>();
	}

	public Laptop() {
		super();
		this.setCategory("laptop");
	}

	public void setAccessories(HashMap<String, String> accessories) {
		this.accessories = accessories;
	}

}

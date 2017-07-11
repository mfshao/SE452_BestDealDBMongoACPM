import java.util.HashMap;


public class Tablet extends Product {
	HashMap<String, String> accessories;

	public Tablet(String id, String name, double price, String image, String manufacturer, String condition,
			double discount) {
		super(id, name, price, image, manufacturer, condition, discount);
		this.setCategory("tablet");
		this.accessories = new HashMap<String, String>();
	}

	public Tablet() {
		super();
		this.setCategory("tablet");
	}

	public void setAccessories(HashMap<String, String> accessories) {
		this.accessories = accessories;
	}

	public HashMap<String, String> getAccessories() {
		return accessories;
	}
	
}

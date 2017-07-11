import java.util.*;

public class TV extends Product {
	HashMap<String, String> accessories;

	public TV(String id, String name, double price, String image, String manufacturer, String condition, double discount) {
		super(id, name, price, image, manufacturer, condition, discount);
		this.setCategory("tv");
		this.accessories = new HashMap<String, String>();
	}

	public TV() {
		super();
		this.setCategory("tv");
	}
	
	HashMap<String, String> getAccessories() {
		return accessories;
	}

	public void setAccessories(HashMap<String, String> accessories) {
		this.accessories = accessories;
	}

}

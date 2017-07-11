import java.util.*;

public class SmartPhone extends Product {
	HashMap<String, String> accessories;

	public SmartPhone(String id, String name, double price, String image, String manufacturer, String condition,
			double discount) {
		super(id, name, price, image, manufacturer, condition, discount);
		this.setCategory("smartphone");
		this.accessories = new HashMap<String, String>();
	}

	public SmartPhone() {
		super();
		this.setCategory("smartphone");
	}

	HashMap<String, String> getAccessories() {
		return accessories;
	}

	public void setAccessories(HashMap<String, String> accessories) {
		this.accessories = accessories;
	}

}

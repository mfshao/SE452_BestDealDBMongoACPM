public class Accessory extends Product {

	public Accessory(String id, String name, double price, String image, String manufacturer, String condition, double discount) {
		super(id, name, price, image, manufacturer, condition, discount);
		this.setCategory("accessory");
	}

	public Accessory() {
		super();
		this.setCategory("accessory");
	}
}

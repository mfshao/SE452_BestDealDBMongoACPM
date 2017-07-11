import java.io.Serializable;

public class OrderPayment implements Serializable {

	private static final long serialVersionUID = 1L;

	private int orderID;
	private int userID;
	private String orderName;
	private double orderPrice;
	private String userAddress;
	private String creditCardNo;
	private String deliveryDate;

	public OrderPayment(int orderID, int userID, String orderName, double orderPrice, String userAddress,
			String creditCardNo, String deliveryDate) {
		this.orderID = orderID;
		this.userID = userID;
		this.orderName = orderName;
		this.orderPrice = orderPrice;
		this.userAddress = userAddress;
		this.creditCardNo = creditCardNo;
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

//	public String getUserName() {
//		return MySQLDataStoreUtilities.getUserNameByID(userID);
//	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQLDataStoreUtilities {
	private final static String DROP_REGISTRATION_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS Registration";
	private final static String DROP_ORDERS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS Orders";
	private final static String DROP_ORDER_ITEMS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS OrderItems";
	private final static String DROP_PRODUCTS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS Products";
	private final static String CREATE_REGISTRATION_TABLE = "CREATE TABLE IF NOT EXISTS Registration (userID INT NOT NULL AUTO_INCREMENT, userName VARCHAR(255) NOT NULL, password VARCHAR(255), userType VARCHAR(255), PRIMARY KEY(userID));";
	private final static String CREATE_ORDERS_TABLE = "CREATE TABLE IF NOT EXISTS Orders (orderID INT NOT NULL AUTO_INCREMENT, userID INT NOT NULL, orderName VARCHAR(255), orderPrice DOUBLE, userAddress VARCHAR(255), creditCardNo VARCHAR(255), deliveryDate VARCHAR(255), PRIMARY KEY(orderID), FOREIGN KEY (userID) REFERENCES Registration(userID));";
	private final static String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS OrderItems (itemID VARCHAR(255) NOT NULL, orderID INT NOT NULL, itemType VARCHAR(255), itemName VARCHAR(255), itemPrice DOUBLE, itemImage VARCHAR(255), itemRetailer VARCHAR(255), itemDiscount DOUBLE, itemAmount INT, itemExtraCost DOUBLE, PRIMARY KEY(itemID, orderID), FOREIGN KEY (orderID) REFERENCES Orders(orderID));";
	private final static String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS Products (productID VARCHAR(255) NOT NULL, productName VARCHAR(255) NOT NULL, productPrice DOUBLE, productImage VARCHAR(255), productManufacturer VARCHAR(255), productCondition VARCHAR(255), productDiscount DOUBLE, productCategory VARCHAR(255), PRIMARY KEY(productID));";
	private final static String ADD_USER = "INSERT INTO Registration (userName, password, userType) VALUES (?, ?, ?);";
	private final static String GET_USER = "SELECT * FROM Registration WHERE userName = ?";
	private final static String GET_ALL_USERIDS_AND_USERNAMES = "SELECT userID, userName FROM Registration";
	private final static String GET_USER_ID_BY_NAME = "SELECT userID FROM Registration WHERE userName = ?";
	private final static String GET_USER_NAME_BY_ID = "SELECT userName FROM Registration WHERE userID = ?";
	private final static String ADD_ORDER = "INSERT INTO Orders (userID, orderName, orderPrice, userAddress, creditCardNo, deliveryDate) VALUES (?, ?, ?, ?, ?, ?);";
	private final static String GET_ALL_ORDERS = "SELECT * FROM Orders;";
	private final static String GET_ORDER_BY_ID = "SELECT * FROM Orders WHERE orderID = ?;";
	private final static String DELETE_ORDER_BY_ID = "DELETE FROM Orders WHERE orderID = ?;";
	private final static String UPDATE_ORDER_BY_ID = "UPDATE Orders SET userAddress = ?, creditCardNo = ?, deliveryDate = ? WHERE orderID = ?;";
	private final static String GET_ORDERS_TABLE_STATUS = "SHOW TABLE STATUS LIKE 'Orders';";
	private final static String ADD_ORDER_ITEM = "INSERT INTO OrderItems (itemID, orderID, itemType, itemName, itemPrice, itemImage, itemRetailer, itemDiscount, itemAmount, itemExtraCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final static String GET_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM OrderItems WHERE orderID = ?;";
	private final static String DELETE_ORDER_ITEMS_BY_ORDER_ID = "DELETE FROM OrderItems WHERE orderID = ?;";
	private final static String ADD_PRODUCT = "INSERT INTO Products (productID, productName, productPrice, productImage, productManufacturer, productCondition, productDiscount, productCategory) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private final static String UPDATE_PRODUCT_BY_ID = "UPDATE Products SET productName = ?, productPrice = ?, productImage = ?, productManufacturer = ?, productCondition = ?, productDiscount = ?, productCategory = ? WHERE productID = ?;";
	private final static String DELETE_PRODUCT_BY_ID = "DELETE FROM Products WHERE productID = ?;";
	
	public static void initalizeDatabase() {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			Statement s = connection.createStatement();
			s.executeUpdate(DROP_ORDER_ITEMS_TABLE_IF_EXISTS);
			s.executeUpdate(DROP_ORDERS_TABLE_IF_EXISTS);
			s.executeUpdate(DROP_REGISTRATION_TABLE_IF_EXISTS);
			s.executeUpdate(DROP_PRODUCTS_TABLE_IF_EXISTS);
			s.executeUpdate(CREATE_REGISTRATION_TABLE);
			s.executeUpdate(CREATE_ORDERS_TABLE);
			s.executeUpdate(CREATE_ORDER_ITEMS_TABLE);
			s.executeUpdate(CREATE_PRODUCTS_TABLE);
			PreparedStatement ps = connection.prepareStatement(ADD_USER);
			ps.setString(1, "aa");
			ps.setString(2, "aa");
			ps.setString(3, "customer");
			ps.executeUpdate();
			ps.setString(1, "bb");
			ps.setString(2, "bb");
			ps.setString(3, "customer");
			ps.executeUpdate();
			ps.setString(1, "cc");
			ps.setString(2, "cc");
			ps.setString(3, "customer");
			ps.executeUpdate();
			ps.setString(1, "dd");
			ps.setString(2, "dd");
			ps.setString(3, "customer");
			ps.executeUpdate();
			ps.setString(1, "ee");
			ps.setString(2, "ee");
			ps.setString(3, "customer");
			ps.executeUpdate();
			ps.setString(1, "as");
			ps.setString(2, "as");
			ps.setString(3, "retailer");
			ps.executeUpdate();
			ps.setString(1, "ad");
			ps.setString(2, "ad");
			ps.setString(3, "manager");
			ps.executeUpdate();
			
			ArrayList<Product> productList = SaxParserDataStore.getAllProducts();
			ps = connection.prepareStatement(ADD_PRODUCT);
			for (Product p : productList) {
				ps.setString(1, p.getId());
				ps.setString(2, p.getName());
				ps.setDouble(3, p.getPrice());
				ps.setString(4, p.getImage());
				ps.setString(5, p.getManufacturer());
				ps.setString(6, p.getCondition());
				ps.setDouble(7, p.getDiscount());
				ps.setString(8, p.getCategory());
				ps.executeUpdate();
			}
			
			ConnectionPool.getInstance().freeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static User getUser(String userName) throws SQLException {
		User user = null;
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_USER);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			user = new User(rs.getString("userName"), rs.getString("password"), rs.getString("userType"));
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return user;
	}

	public static void addUser(User user) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(ADD_USER);
		ps.setString(1, user.getName());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getUsertype());
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}

	public static HashMap<Integer, String> getAllUserIDsAndNames() throws SQLException {
		HashMap<Integer, String> userIDNameMap = new HashMap<Integer, String>();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_ALL_USERIDS_AND_USERNAMES);
		ResultSet rs = ps.executeQuery();
		rs.last();
		int numberOfRows = rs.getRow();
		rs.beforeFirst();
		while (numberOfRows > 0 && rs.next()) {
			Integer userID = rs.getInt("userID");
			String userName = rs.getString("userName");
			userIDNameMap.put(userID, userName);
			numberOfRows--;
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return userIDNameMap;
	}

	public static int getUserIDByName(String userName) throws SQLException {
		int userID = -1;
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_USER_ID_BY_NAME);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			userID = rs.getInt("userID");
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return userID;
	}

	public static String getUserNameByID(int userID) throws SQLException {
		String userName = null;
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_USER_NAME_BY_ID);
		ps.setInt(1, userID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			userName = rs.getString("userName");
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return userName;
	}

	public static void addOrder(OrderPayment op) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(ADD_ORDER);
		ps.setInt(1, op.getUserID());
		ps.setString(2, op.getOrderName());
		ps.setDouble(3, op.getOrderPrice());
		ps.setString(4, op.getUserAddress());
		ps.setString(5, op.getCreditCardNo());
		ps.setString(6, op.getDeliveryDate());
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}

	public static int getNextOrderID() throws SQLException {
		int nextOrderID = -1;
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_ORDERS_TABLE_STATUS);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			nextOrderID = rs.getInt("Auto_increment");
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return nextOrderID;
	}

	public static void addOrderItem(OrderItem oi, int orderID) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(ADD_ORDER_ITEM);
		ps.setString(1, oi.getItemID());
		ps.setInt(2, orderID);
		ps.setString(3, oi.getType());
		ps.setString(4, oi.getName());
		ps.setDouble(5, oi.getPrice());
		ps.setString(6, oi.getImage());
		ps.setString(7, oi.getManufacturer());
		ps.setDouble(8, oi.getDiscount());
		ps.setInt(9, oi.getAmount());
		ps.setDouble(10, oi.getExtraCost());
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}

	public static OrderPayment getOrderByID(int orderID) throws SQLException {
		OrderPayment op = null;
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_ORDER_BY_ID);
		ps.setInt(1, orderID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			op = new OrderPayment(orderID, rs.getInt("userID"), rs.getString("orderName"), rs.getDouble("orderPrice"),
					rs.getString("userAddress"), rs.getString("creditCardNo"), rs.getString("deliveryDate"));
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return op;
	}

	public static ArrayList<OrderPayment> getAllOrders() throws SQLException {
		ArrayList<OrderPayment> orderPaymentList = new ArrayList<OrderPayment>();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_ALL_ORDERS);
		ResultSet rs = ps.executeQuery();
		rs.last();
		int numberOfRows = rs.getRow();
		rs.beforeFirst();
		while (numberOfRows > 0 && rs.next()) {
			OrderPayment op = new OrderPayment(rs.getInt("orderID"), rs.getInt("userID"), rs.getString("orderName"),
					rs.getDouble("orderPrice"), rs.getString("userAddress"), rs.getString("creditCardNo"),
					rs.getString("deliveryDate"));
			orderPaymentList.add(op);
			numberOfRows--;
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return orderPaymentList;
	}

	public static ArrayList<OrderItem> getOrderItemsByOrderID(int orderID) throws SQLException {
		ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(GET_ORDER_ITEMS_BY_ORDER_ID);
		ps.setInt(1, orderID);
		ResultSet rs = ps.executeQuery();
		rs.last();
		int numberOfRows = rs.getRow();
		rs.beforeFirst();
		while (numberOfRows > 0 && rs.next()) {
			OrderItem oi = new OrderItem(rs.getString("itemID"), rs.getInt("orderID"), rs.getString("itemType"),
					rs.getString("itemName"), rs.getDouble("itemPrice"), rs.getString("itemImage"),
					rs.getString("itemRetailer"), rs.getDouble("itemDiscount"), rs.getInt("itemAmount"),
					rs.getDouble("itemExtraCost"));
			orderItemList.add(oi);
			numberOfRows--;
		}
		ConnectionPool.getInstance().freeConnection(connection);
		return orderItemList;
	}

	public static void deleteOrderItemsByOrderID(int orderID) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE_ORDER_ITEMS_BY_ORDER_ID);
		ps.setInt(1, orderID);
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}

	public static void deleteOrderByID(int orderID) throws SQLException {
		deleteOrderItemsByOrderID(orderID);
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE_ORDER_BY_ID);
		ps.setInt(1, orderID);
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}

	public static void updateOrderByID(int orderID, String userAddress, String creditCardNo, String deliveryDate)
			throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_BY_ID);
		ps.setString(1, userAddress);
		ps.setString(2, creditCardNo);
		ps.setString(3, deliveryDate);
		ps.setInt(4, orderID);
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}
	
	public static void updateProductByID(Product p)
			throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
		ps.setString(1, p.getName());
		ps.setDouble(2, p.getPrice());
		ps.setString(3, p.getImage());
		ps.setString(4, p.getManufacturer());
		ps.setString(5, p.getCondition());
		ps.setDouble(6, p.getDiscount());
		ps.setString(7, p.getCategory());
		ps.setString(8, p.getId());
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}
	
	public static void deleteProductByID(String productID) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
		ps.setString(1, productID);
		ps.executeUpdate();
		ConnectionPool.getInstance().freeConnection(connection);
	}
}

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet("/Utilities")

public class Utilities extends HttpServlet {

	private static final long serialVersionUID = 1L;

	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;

	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	public void printHtml(String file) {
		String result = HtmlToString(file);
		if (file == "Header.html") {
			result = result + "<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username") != null) {
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<li><a href='ViewOrder'>ViewOrder</a></li>" + "<li><a>Hello," + username
						+ "</a></li>" + "<li><a href='Account'>Account</a></li>"
						+ "<li><a href='Logout'>Logout</a></li>";
			} else
				result = result + "<li><a href='ViewOrder'>View Order</a></li>" + "<li><a href='Login'>Login</a></li>";
			result = result + "<li><a href='Cart'>Cart(" + CartCount() + ")</a></li></ul></div></div><div id='page'>";
			pw.print(result);
		} else
			pw.print(result);
	}

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} catch (Exception e) {
		}
		return result;
	}

	public void logout() {
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	public boolean isLoggedin() {
		if (session.getAttribute("username") == null)
			return false;
		return true;
	}

	public String username() {
		if (session.getAttribute("username") != null)
			return session.getAttribute("username").toString();
		return null;
	}

	public String usertype() {
		if (session.getAttribute("usertype") != null)
			return session.getAttribute("usertype").toString();
		return null;
	}

	public User getUser() throws SQLException {
		User user = MySQLDataStoreUtilities.getUser(username());
		return user;
	}
	
	public User getUser(String userName) throws SQLException {
		User user = MySQLDataStoreUtilities.getUser(userName);
		return user;
	}

	public ArrayList<OrderItem> getCurrentOrderItems() {
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if (OrdersHashMap.orders.containsKey(username()))
			order = OrdersHashMap.orders.get(username());
		return order;
	}

	public int CartCount() {
		if (isLoggedin())
			return getCurrentOrderItems().size();
		return 0;
	}
	
	public void storeCurrentOrderItems(String userName, String name, String type, int amount) {
		storeCurrentOrderItems(userName, name, type, "", "", amount, 0.0);
	}

	public void storeCurrentOrderItems(String userName, String name, String type, String maker, String access, int amount, double extraCost) {
		if (!OrdersHashMap.orders.containsKey(userName)) {
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(userName, arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(userName);
		if (type.equals("smartphone")) {
			SmartPhone smartphone;
			smartphone = SaxParserDataStore.smartphones.get(name);
			for (OrderItem oi : orderItems) {
				if (oi.getItemID().equalsIgnoreCase(name)) {
					oi.setAmount(oi.getAmount() + 1);
					return;
				}
			}
			OrderItem orderitem = new OrderItem(name, Properties.DUMMY_ORDER_ID, type, smartphone.getName(), smartphone.getPrice(),
					smartphone.getImage(), smartphone.getManufacturer(), smartphone.getDiscount(), amount, extraCost);
			orderItems.add(orderitem);
		}
		if (type.equals("laptop")) {
			Laptop laptop = null;
			laptop = SaxParserDataStore.laptops.get(name);
			for (OrderItem oi : orderItems) {
				if (oi.getItemID().equalsIgnoreCase(name)) {
					oi.setAmount(oi.getAmount() + 1);
					return;
				}
			}
			OrderItem orderitem = new OrderItem(name, Properties.DUMMY_ORDER_ID, type, laptop.getName(), laptop.getPrice(), laptop.getImage(),
					laptop.getManufacturer(), laptop.getDiscount(), amount, extraCost);
			orderItems.add(orderitem);
		}
		if (type.equals("tablet")) {
			Tablet tablet = null;
			tablet = SaxParserDataStore.tablets.get(name);
			for (OrderItem oi : orderItems) {
				if (oi.getItemID().equalsIgnoreCase(name)) {
					oi.setAmount(oi.getAmount() + 1);
					return;
				}
			}
			OrderItem orderitem = new OrderItem(name, Properties.DUMMY_ORDER_ID, type, tablet.getName(), tablet.getPrice(), tablet.getImage(),
					tablet.getManufacturer(), tablet.getDiscount(), amount, extraCost);
			orderItems.add(orderitem);
		}
		if (type.equals("tv")) {
			TV tv = null;
			tv = SaxParserDataStore.tvs.get(name);
			for (OrderItem oi : orderItems) {
				if (oi.getItemID().equalsIgnoreCase(name)) {
					oi.setAmount(oi.getAmount() + 1);
					return;
				}
			}
			OrderItem orderitem = new OrderItem(name, Properties.DUMMY_ORDER_ID, type, tv.getName(), tv.getPrice(), tv.getImage(),
					tv.getManufacturer(), tv.getDiscount(), amount, extraCost);
			orderItems.add(orderitem);
		}
		if (type.equals("accessory")) {
			Accessory accessory = null;
			accessory = SaxParserDataStore.accessories.get(name);
			for (OrderItem oi : orderItems) {
				if (oi.getItemID().equalsIgnoreCase(name)) {
					oi.setAmount(oi.getAmount() + 1);
					return;
				}
			}
			OrderItem orderitem = new OrderItem(name, Properties.DUMMY_ORDER_ID, type, accessory.getName(), accessory.getPrice(), accessory.getImage(),
					accessory.getManufacturer(), accessory.getDiscount(), amount, extraCost);
			orderItems.add(orderitem);
		}
	}

	public void storePayment(String userName, int orderID, double orderPrice, String userAddress,
			String creditCardNo, String deliveryDate) throws SQLException {
		OrderPayment op = new OrderPayment(orderID, MySQLDataStoreUtilities.getUserIDByName(userName), userName + orderID, orderPrice, userAddress,
				creditCardNo, deliveryDate);
		MySQLDataStoreUtilities.addOrder(op);
	}
	
	public double calculatePrice(OrderItem oi) {
		return (Math.round(oi.getPrice() * (100 - oi.getDiscount())) / 100.0) + oi.getExtraCost();
	}

	public HashMap<String, SmartPhone> getSmartPhones() {
		HashMap<String, SmartPhone> hm = new HashMap<String, SmartPhone>();
		hm.putAll(SaxParserDataStore.smartphones);
		return hm;
	}

	public HashMap<String, Laptop> getLaptops() {
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
		hm.putAll(SaxParserDataStore.laptops);
		return hm;
	}

	public HashMap<String, Tablet> getTablets() {
		HashMap<String, Tablet> hm = new HashMap<String, Tablet>();
		hm.putAll(SaxParserDataStore.tablets);
		return hm;
	}

	public HashMap<String, TV> getTVs() {
		HashMap<String, TV> hm = new HashMap<String, TV>();
		hm.putAll(SaxParserDataStore.tvs);
		return hm;
	}

	public void deleteOrderItem(String name) {
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		ListIterator<OrderItem> iter = orderItems.listIterator();
		while (iter.hasNext()) {
			if (iter.next().getItemID().equalsIgnoreCase(name)) {
				iter.remove();
			}
		}
	}

	public void updateProductAmount(String name, int amount) {
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		for (OrderItem oi : orderItems) {
			if (oi.getItemID().equalsIgnoreCase(name)) {
				oi.setAmount(amount);
				return;
			}
		}
	}
	
	public String parseDateToString(Date date) {
		if (date == null) {
			return "";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			return dateFormat.format(date);
		}
	}
	
	public Date parseStringToDate(String dateStr) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public void storeOrderItems(int orderID, ArrayList<OrderItem> orderItems) throws SQLException {
		for (OrderItem oi : orderItems) {
			MySQLDataStoreUtilities.addOrderItem(oi, orderID);
		}
	}
	
	public Product getProductByNameAndType(String name, String type) {
		Product p = null;
		
		if (type.equals("smartphone")) {
			p = SaxParserDataStore.smartphones.get(name);
		}
		if (type.equals("laptop")) {
			p = SaxParserDataStore.laptops.get(name);
		}
		if (type.equals("tablet")) {
			p = SaxParserDataStore.tablets.get(name);
		}
		if (type.equals("tv")) {
			p = SaxParserDataStore.tvs.get(name);
		}
		if (type.equals("accessory")) {
			p = SaxParserDataStore.accessories.get(name);
		}
		return p;
	}

}

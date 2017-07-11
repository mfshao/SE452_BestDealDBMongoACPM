import java.util.HashMap;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/OrdersHashMap")

public class OrdersHashMap extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static HashMap<String, ArrayList<OrderItem>> orders = new HashMap<String, ArrayList<OrderItem>>();

	public OrdersHashMap() {

	}
}

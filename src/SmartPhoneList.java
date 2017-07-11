import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartPhoneList")

public class SmartPhoneList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
		String id = request.getParameter("id");

		HashMap<String, SmartPhone> hm = new HashMap<String, SmartPhone>();
		if (CategoryName != null) {
			if (CategoryName.equals("apple")) {
				for (Map.Entry<String, SmartPhone> entry : SaxParserDataStore.smartphones.entrySet()) {
					if (entry.getValue().getManufacturer().equals("Apple")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Apple";
			} else if (CategoryName.equals("samsung")) {
				for (Map.Entry<String, SmartPhone> entry : SaxParserDataStore.smartphones.entrySet()) {
					if (entry.getValue().getManufacturer().equals("Samsung")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Samsung";
			} else if (CategoryName.equals("huawei")) {
				for (Map.Entry<String, SmartPhone> entry : SaxParserDataStore.smartphones.entrySet()) {
					if (entry.getValue().getManufacturer().equals("Huawei")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Huawei";
			}
		} else if (id != null) {
			for (Map.Entry<String, SmartPhone> entry : SaxParserDataStore.smartphones.entrySet()) {
				if (entry.getKey().equals(id)) {
					hm.put(entry.getKey(), entry.getValue());
					name = "Searched";
				}
			}
		} else {
			hm.putAll(SaxParserDataStore.smartphones);
			name = "";
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " Smart Phones</a>");
		pw.print("</h2><div class='entry'><table id='smartphonetable'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, SmartPhone> entry : hm.entrySet()) {
			SmartPhone smartphone = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + smartphone.getName() + "</h3>");
			pw.print("<strong><del>$" + smartphone.getPrice() + "</del> <font color=\"red\">$"
					+ Math.round(smartphone.getPrice() * (100 - smartphone.getDiscount())) / 100.0
					+ "</font></strong><ul>");
			pw.print("<li id='item'><img src='images/smartphones/" + smartphone.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='smartphone'>"
					+ "<input type='hidden' name='maker' value='" + smartphone.getManufacturer() + "'>"
					+ "<input type='hidden' name='Cart' value='DisplayCart'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy Now'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 1yr Care $14.99'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 2yr Care $28.99'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 3yr Care $43.99'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='smartphone'>"
					+ "<input type='hidden' name='maker' value='" + smartphone.getManufacturer() + "'>"
					+ "<input type='hidden' name='Review' value='WriteReview'>"
					+ "<input type='submit' class='btnyes' name='ReviewBtn' value='WriteReview'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='smartphone'>"
					+ "<input type='hidden' name='maker' value='" + smartphone.getManufacturer() + "'>"
					+ "<input type='hidden' name='Review' value='ViewReview'>"
					+ "<input type='submit' class='btnyes' name='ReviewBtn' value='ViewReview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");

	}
}

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LaptopList")

public class LaptopList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String name = null;
		String CategoryName = request.getParameter("maker");
		String id = request.getParameter("id");
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();

		if (CategoryName != null) {
			if (CategoryName.equals("dell")) {
				for (Map.Entry<String, Laptop> entry : SaxParserDataStore.laptops.entrySet()) {
					if (entry.getValue().getManufacturer().equals("Dell")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Dell";
			} else if (CategoryName.equals("apple")) {
				for (Map.Entry<String, Laptop> entry : SaxParserDataStore.laptops.entrySet()) {
					if (entry.getValue().getManufacturer().equals("Apple")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
				name = "Apple";
			}
		} else if (id != null) {
			for (Map.Entry<String, Laptop> entry : SaxParserDataStore.laptops.entrySet()) {
				if (entry.getKey().equals(id)) {
					hm.put(entry.getKey(), entry.getValue());
					name = "Searched";
				}
			}
		} else {
			hm.putAll(SaxParserDataStore.laptops);
			name = "";
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " Laptops</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Laptop> entry : hm.entrySet()) {
			Laptop laptop = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + laptop.getName() + "</h3>");
			pw.print("<strong><del>$" + laptop.getPrice() + "</del> <font color=\"red\">$"
					+ Math.round(laptop.getPrice() * (100 - laptop.getDiscount())) / 100.0 + "</font></strong><ul>");
			pw.print("<li id='item'><img src='images/laptops/" + laptop.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='laptop'>"
					+ "<input type='hidden' name='maker' value='" + laptop.getManufacturer() + "'>"
					+ "<input type='hidden' name='access' value=''>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy Now'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 1yr Care $34.99'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 2yr Care $69.99'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 3yr Care $99.99'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='laptop'>"
					+ "<input type='hidden' name='maker' value='" + laptop.getManufacturer() + "'>"
					+ "<input type='hidden' name='Review' value='WriteReview'>"
					+ "<input type='submit' class='btnyes' name='ReviewBtn' value='WriteReview'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='laptop'>"
					+ "<input type='hidden' name='maker' value='" + laptop.getManufacturer() + "'>"
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

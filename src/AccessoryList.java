import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccessoryList")

public class AccessoryList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String id = request.getParameter("id");
		String name = null;
		HashMap<String, Accessory> hm = new HashMap<String, Accessory>();
		if (id != null) {
			for (Map.Entry<String, Accessory> entry : SaxParserDataStore.accessories.entrySet()) {
				if (entry.getKey().equals(id)) {
					hm.put(entry.getKey(), entry.getValue());
					name = "Searched";
				}
			}
		} else {
			hm.putAll(SaxParserDataStore.accessories);
			name = "";
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " Accessories</a>");
		pw.print("</h2><div class='entry'><table id='smartphonetable'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Accessory> entry : hm.entrySet()) {
			Accessory accessory = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + accessory.getName() + "</h3>");
			pw.print("<strong><del>$" + accessory.getPrice() + "</del> <font color=\"red\">$"
					+ Math.round(accessory.getPrice() * (100 - accessory.getDiscount())) / 100.0
					+ "</font></strong><ul>");
			pw.print("<li id='item'><img src='images/accessories/" + accessory.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='accessory'>"
					+ "<input type='hidden' name='maker' value='" + accessory.getManufacturer() + "'>"
					+ "<input type='hidden' name='Cart' value='DisplayCart'>"
					+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='accessory'>"
					+ "<input type='hidden' name='maker' value='" + accessory.getManufacturer()+ "'>"
					+ "<input type='hidden' name='Review' value='WriteReview'>"
					+ "<input type='submit' class='btnyes' name='ReviewBtn' value='WriteReview'></form></li>");
			pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
					+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='accessory'>"
					+ "<input type='hidden' name='maker' value='" + accessory.getManufacturer() + "'>"
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

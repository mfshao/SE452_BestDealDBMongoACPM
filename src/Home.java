import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")

public class Home extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		HashMap<String, Product> selectedProductMap = new HashMap<>();
		ArrayList<String> offerList = new ArrayList<>();
		HashMap<String, Product> productMap = SaxParserDataStore.getAllProductsAsHashMap();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Properties.TOMCAT_HOME
					+ Properties.WEBAPP_PATH + Properties.PROJECT_PATH + Properties.PRICE_MATCH_FILE_PATH)));
			while ((line = br.readLine()) != null) {
				for (Entry<String, Product> entry : productMap.entrySet()) {
					if (selectedProductMap.size() < 2) {
						if (line.contains(entry.getValue().getName())
								&& !selectedProductMap.containsKey(entry.getKey())) {
							offerList.add(line);
							selectedProductMap.put(entry.getKey(), entry.getValue());
						}
					} else {
						break;
					}
				}
			}
			br.close();
		} catch (Exception e) {
		}

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'>");
		pw.print("<div class='post'>");
		pw.print("<h2 class='title'>");
		pw.print("<a>Welcome to BestDeal - The best electronics deal site</a>");
		pw.print("</h2>");
		pw.print("<div class='entry'>");
		pw.print(
				"<img src='images/site/home.jpg' style='width: 600px; display: block; margin-left: auto; margin-right: auto' />");
		pw.print("<h2 class='title' align='center'>Price-Match Guaranteed</h2>");
		if (offerList.isEmpty()) {
			pw.print("<h3 align='center'>No Offers Found</h3>");
		} else {
			for (String s : offerList) {
				pw.print("<h3 align='center'>" + s + "</h3>");
			}
		}
		pw.print("</div>");
		pw.print("</div>");
		pw.print("</div>");

		pw.print("<div id='content'>");
		pw.print("<div class='post'>");
		pw.print("<h2 class='title'>Deal Matches</h2>");
		pw.print("<div class='entry'>");
		if (!selectedProductMap.isEmpty()) {
			pw.print("<table id='bestseller'>");
			int i = 1;
			for (Map.Entry<String, Product> entry : selectedProductMap.entrySet()) {
				Product p = entry.getValue();
				String imgPath = "images/";
				String type = "";
				if (p instanceof SmartPhone) {
					imgPath += "smartphones/" + p.getImage();
					type = "smartphone";
				} else if (p instanceof Laptop) {
					imgPath += "laptops/" + p.getImage();
					type = "laptop";
				} else if (p instanceof Tablet) {
					imgPath += "tablets/" + p.getImage();
					type = "tablet";
				} else if (p instanceof Accessory) {
					imgPath += "accessories/" + p.getImage();
					type = "accessory";
				} else if (p instanceof TV) {
					imgPath += "tvs/" + p.getImage();
					type = "tv";
				}
				if (i % 3 == 1) {
					pw.print("<tr>");
				}
				pw.print("<td><div id='shop_item'>");
				pw.print("<h3>" + p.getName() + "</h3>");
				pw.print("<strong><del>$" + p.getPrice() + "</del> <font color=\"red\">$"
						+ Math.round(p.getPrice() * (100 - p.getDiscount())) / 100.0 + "</font></strong><ul>");
				pw.print("<li id='item'><img src='" + imgPath + "' alt='' /></li>");
				pw.print("<li><form method='post' action='Cart'>" + "<input type='hidden' name='name' value='"
						+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='" + type + "'>"
						+ "<input type='hidden' name='maker' value='" + p.getManufacturer() + "'>"
						+ "<input type='hidden' name='access' value=''>"
						+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy Now'>"
						+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 1yr Care $34.99'>"
						+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 2yr Care $69.99'>"
						+ "<input type='submit' class='btnnormal' name='BuyBtn' value='Buy with 3yr Care $99.99'></form></li>");
				pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
						+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='" + type + "'>"
						+ "<input type='hidden' name='maker' value='" + p.getManufacturer() + "'>"
						+ "<input type='hidden' name='Review' value='WriteReview'>"
						+ "<input type='submit' class='btnyes' name='ReviewBtn' value='WriteReview'></form></li>");
				pw.print("<li><form method='post' action='Review'>" + "<input type='hidden' name='name' value='"
						+ entry.getKey() + "'>" + "<input type='hidden' name='type' value='" + type + "'>"
						+ "<input type='hidden' name='maker' value='" + p.getManufacturer() + "'>"
						+ "<input type='hidden' name='Review' value='ViewReview'>"
						+ "<input type='submit' class='btnyes' name='ReviewBtn' value='ViewReview'></form></li>");
				pw.print("</ul></div></td>");
				if (i == selectedProductMap.size()) {
					pw.print("</tr>");
				}
				i++;
			}
			pw.print("</table>");
		} else {
			pw.print("<h3 align='center'>No Deals Found</h3>");
		}
		pw.print("</div>");
		pw.print("</div>");
		pw.print("</div>");
		utility.printHtml("Footer.html");

	}

}

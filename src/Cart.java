import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Cart")

public class Cart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		if (request.getParameter("Cart") != null && request.getParameter("Cart").equals("CheckOut")) {
			RequestDispatcher rd = request.getRequestDispatcher("CheckOut");
			rd.forward(request, response);
		} else if (request.getParameter("Update") != null) {
			Utilities utility = new Utilities(request, pw);
			String segments[] = request.getParameter("Update").split(" ");
			int amount = Integer.parseInt(request.getParameter("orderAmount" + segments[1]));
			utility.updateProductAmount(segments[1], amount);
			displayCart(request, response);
		} else if (request.getParameter("Delete") != null) {
			Utilities utility = new Utilities(request, pw);
			String segments[] = request.getParameter("Delete").split(" ");
			utility.deleteOrderItem(segments[1]);
			displayCart(request, response);
		} else {
			Utilities utility = new Utilities(request, pw);
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String maker = request.getParameter("maker");
			String access = request.getParameter("access");
			String buyBtnVal = request.getParameter("BuyBtn");
			double extraCost = 0.0;
			if (buyBtnVal.contains("$")) {
				String[] segment = buyBtnVal.split("\\$");
				extraCost = Double.parseDouble(segment[1]);
			}

			utility.storeCurrentOrderItems(utility.username(), name, type, maker, access, 1, extraCost);
			displayCart(request, response);
		}
	}

	protected void displayCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='Cart' action='Cart' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Cart(" + utility.CartCount() + ")</a>");
		pw.print("</h2><div class='entry'>");
		if (utility.CartCount() > 0) {
			pw.print("<table  class='gridtable'>");
			pw.print("<tr><td></td>");
			pw.print("<td>ItemName:</td>");
			pw.print("<td>ProductPrice:</td>");
			pw.print("<td>Amount:</td>");
			pw.print("<td>Update:</td>");
			pw.print("<td>Delete:</td>");
			int i = 1;
			double total = 0.0;
			for (OrderItem oi : utility.getCurrentOrderItems()) {
				pw.print("<tr>");
				pw.print("<td>" + i + ".</td><td>" + oi.getName() + "</td><td>: "
						+ utility.calculatePrice(oi)
						+ "</td><td>: <input type='text' size='4' name='orderAmount" + oi.getItemID() + "' value='"
						+ oi.getAmount() + "'></td>");
				pw.print("<td><input type='submit' name='Update' value='Update " + oi.getItemID()
						+ "' class='btnnormal'></td>");
				pw.print("<td><input type='submit' name='Delete' value='Delete " + oi.getItemID()
						+ "' class='btnno'></td>");
				pw.print("<input type='hidden' name='orderItemId' value='" + oi.getItemID() + "'>");
				pw.print("<input type='hidden' name='orderName' value='" + oi.getName() + "'>");
				pw.print("<input type='hidden' name='orderPrice' value='"
						+ utility.calculatePrice(oi) + "'>");
				pw.print("</tr>");
				total = total + (utility.calculatePrice(oi) * oi.getAmount());
				i++;
			}
			total = Double.parseDouble(new DecimalFormat("#.00").format(total));
			pw.print("<input type='hidden' name='orderTotal' value='" + total + "'>");
			pw.print("<tr><th></th><th>Total</th><th>" + total + "</th>");
			pw.print(
					"<tr><td></td><td></td><td><input type='submit' name='Cart' value='CheckOut' class='btnyes'></td>");
			pw.print("</table>");
		} else {
			pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
		}
		pw.print("</div></div></div>");
		pw.print("</form>");
		utility.printHtml("Footer.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		@SuppressWarnings("unused")
		Utilities utility = new Utilities(request, pw);

		displayCart(request, response);
	}
}

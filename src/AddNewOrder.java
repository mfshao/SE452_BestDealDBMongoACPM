import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddNewOrder")

public class AddNewOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);

		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to Add New Orders");
			response.sendRedirect("Login");
			return;
		}
		
		HashMap<Integer, String> userIDNameMap = new HashMap<>();
		try {
			userIDNameMap = MySQLDataStoreUtilities.getAllUserIDsAndNames();
		} catch (SQLException e) {
			RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
			request.setAttribute("errorType", "MySQL");
			rd.forward(request, response);
		}
		HashMap<String, SmartPhone> smartphones = SaxParserDataStore.smartphones;
		HashMap<String, Laptop> laptops = SaxParserDataStore.laptops;
		HashMap<String, Tablet> tablets = SaxParserDataStore.tablets;
		HashMap<String, TV> tvs = SaxParserDataStore.tvs;
		HashMap<String, Accessory> accessories = SaxParserDataStore.accessories;
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='AddNewOrder' action='Payment' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Add New Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table  class='gridtable'>");
		
		pw.print("<tr><td colspan='4'><b>Product Information</b></td></tr>");
		if (!smartphones.isEmpty()) {
			for (String s : smartphones.keySet()){
				pw.print("<tr><td><input type='checkbox' name='"+s+"'>"+s+"</td>");
				pw.print("<td>"+smartphones.get(s).getName()+"</td>");
				pw.print("<td>Price: $"+(Math.round(smartphones.get(s).getPrice() * (100 - smartphones.get(s).getDiscount())) / 100.0)+"</td>");
				pw.print("<td>Amount: <select name='amount" + s + "'>");
				pw.print("<option value='1'>" + 1 + "</option>");
				pw.print("<option value='2'>" + 2 + "</option>");
				pw.print("<option value='3'>" + 3 + "</option>");
				pw.print("<option value='4'>" + 4 + "</option>");
				pw.print("<option value='5'>" + 5 + "</option>");
				pw.print("</select></td>");
				pw.print("</tr>");
			}
		}
		if (!laptops.isEmpty()) {
			for (String s : laptops.keySet()){
				pw.print("<tr><td><input type='checkbox' name='"+s+"'>"+s+"</td>");
				pw.print("<td>"+laptops.get(s).getName()+"</td>");
				pw.print("<td>Price: $"+(Math.round(laptops.get(s).getPrice() * (100 - laptops.get(s).getDiscount())) / 100.0)+"</td>");
				pw.print("<td>Amount: <select name='amount" + s + "'>");
				pw.print("<option value='1'>" + 1 + "</option>");
				pw.print("<option value='2'>" + 2 + "</option>");
				pw.print("<option value='3'>" + 3 + "</option>");
				pw.print("<option value='4'>" + 4 + "</option>");
				pw.print("<option value='5'>" + 5 + "</option>");
				pw.print("</select></td>");
				pw.print("</tr>");
			}
		}
		if (!smartphones.isEmpty()) {
			for (String s : tablets.keySet()){
				pw.print("<tr><td><input type='checkbox' name='"+s+"'>"+s+"</td>");
				pw.print("<td>"+tablets.get(s).getName()+"</td>");
				pw.print("<td>Price: $"+(Math.round(tablets.get(s).getPrice() * (100 - tablets.get(s).getDiscount())) / 100.0)+"</td>");
				pw.print("<td>Amount: <select name='amount" + s + "'>");
				pw.print("<option value='1'>" + 1 + "</option>");
				pw.print("<option value='2'>" + 2 + "</option>");
				pw.print("<option value='3'>" + 3 + "</option>");
				pw.print("<option value='4'>" + 4 + "</option>");
				pw.print("<option value='5'>" + 5 + "</option>");
				pw.print("</select></td>");
				pw.print("</tr>");
			}
		}
		if (!tvs.isEmpty()) {
			for (String s : tvs.keySet()){
				pw.print("<tr><td><input type='checkbox' name='"+s+"'>"+s+"</td>");
				pw.print("<td>"+tvs.get(s).getName()+"</td>");
				pw.print("<td>Price: $"+(Math.round(tvs.get(s).getPrice() * (100 - tvs.get(s).getDiscount())) / 100.0)+"</td>");
				pw.print("<td>Amount: <select name='amount" + s + "'>");
				pw.print("<option value='1'>" + 1 + "</option>");
				pw.print("<option value='2'>" + 2 + "</option>");
				pw.print("<option value='3'>" + 3 + "</option>");
				pw.print("<option value='4'>" + 4 + "</option>");
				pw.print("<option value='5'>" + 5 + "</option>");
				pw.print("</select></td>");
				pw.print("</tr>");
			}
		}
		if (!accessories.isEmpty()) {
			for (String s : accessories.keySet()){
				pw.print("<tr><td><input type='checkbox' name='"+s+"'>"+s+"</td>");
				pw.print("<td>"+accessories.get(s).getName()+"</td>");
				pw.print("<td>Price: $"+(Math.round(accessories.get(s).getPrice() * (100 - accessories.get(s).getDiscount())) / 100.0)+"</td>");
				pw.print("<td>Amount: <select name='amount" + s + "'>");
				pw.print("<option value='1'>" + 1 + "</option>");
				pw.print("<option value='2'>" + 2 + "</option>");
				pw.print("<option value='3'>" + 3 + "</option>");
				pw.print("<option value='4'>" + 4 + "</option>");
				pw.print("<option value='5'>" + 5 + "</option>");
				pw.print("</select></td>");
				pw.print("</tr>");
			}
		}
		
		pw.print("<tr><td colspan='4'><hr /></td></tr>");
		pw.print("<tr><td colspan='4'><b>Customer Information</b></td></tr>");
		if (!userIDNameMap.isEmpty()) {
			pw.print("<tr><td colspan='2'>Customer Name</td><td colspan='2'>");
			pw.print("<select name='customerUserName'>");
			for (Integer id : userIDNameMap.keySet()) {
				pw.print("<option value='" + userIDNameMap.get(id) + "'>" + userIDNameMap.get(id) + "</option>");
			}
			pw.print("</select>");
			pw.print("</td></tr>");
			pw.print("<tr><td colspan='2'>Customer Address</td><td colspan='2'><input type='text' name='userAddress' size='30' /></td></tr>");
			pw.print("<tr><td colspan='2'>Credit Card Number</td><td colspan='2'><input type='text' name='creditCardNo' size='30' /></td></tr>");
		}
		pw.print("<tr><td colspan='2'>&nbsp;</td><td colspan='2'><input type='submit' name='AddNewOrder' value='PlaceOrder' class='btnyes' /></td></tr>");
		
		pw.print("</table>");
		pw.print("</form></div></div></div>");
		utility.printHtml("Footer.html");
	}
}

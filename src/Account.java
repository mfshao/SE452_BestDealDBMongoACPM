import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Account")

public class Account extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter();
		displayAccount(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter();
		
		if (request.getParameter("ByUser") != null && request.getParameter("ByUser").equals("Create Customer")) {
			RequestDispatcher rd = request.getRequestDispatcher("CreateCustomer");
			rd.forward(request, response);
		} else if (request.getParameter("ByUser") != null && request.getParameter("ByUser").equals("Manage Orders")) {
			RequestDispatcher rd = request.getRequestDispatcher("ManageOrders");
			rd.forward(request, response);
		} else if (request.getParameter("ByUser") != null && request.getParameter("ByUser").equals("Add New Order")) {
			RequestDispatcher rd = request.getRequestDispatcher("AddNewOrder");
			rd.forward(request, response);
		} else if (request.getParameter("ByUser") != null && request.getParameter("ByUser").equals("Manage Products")) {
			RequestDispatcher rd = request.getRequestDispatcher("ManageProducts");
			rd.forward(request, response);
		}
	}

	protected void displayAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try {
			response.setContentType("text/html");
			if (!utility.isLoggedin()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}
			request.getSession();
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Account</a>");
			pw.print("</h2><div class='entry'>");
			User user = utility.getUser();
			pw.print("<table class='gridtable'>");
			pw.print("<tr>");
			pw.print("<td> User Name: </td>");
			pw.print("<td>" + user.getName() + "</td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<td> User Type: </td>");
			pw.print("<td>" + user.getUsertype() + "</td>");
			pw.print("</tr>");
			if (user.getUsertype().equalsIgnoreCase("retailer")) {
				pw.print("<tr>");
				pw.print("<form method='post' action='Account'>");
				pw.print("<td> Operations: </td>");
				pw.print("<td><input type='submit' class='btnnormal' name='ByUser' value='Create Customer'></input>");
				pw.print("<input type='submit' class='btnnormal' name='ByUser' value='Manage Orders'></input>");
				pw.print("<input type='hidden' name='Order' value='ManageOrder'></input>");
				pw.print("<input type='submit' class='btnnormal' name='ByUser' value='Add New Order'></input></td>");
				pw.print("<input type='hidden' name='Order' value='AddOrder'></input>");
				pw.print("</form>");
				pw.print("</tr>");
			}
			if (user.getUsertype().equalsIgnoreCase("manager")) {
				pw.print("<tr>");
				pw.print("<form method='post' action='Account'>");
				pw.print("<td> Operations: </td>");
				pw.print("<td><input type='submit' class='btnnormal' name='ByUser' value='Manage Products'></input></td>");
				pw.print("</form>");
				pw.print("</tr>");
			}
			pw.print("</table>");
			pw.print("</h2></div></div></div>");

			utility.printHtml("Footer.html");
		} catch (Exception e) {
		}
	}
}

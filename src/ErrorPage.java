import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ErrorPage")

public class ErrorPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Error Occured</a>");
		pw.print("</h2><div class='entry'>");
		if (request.getAttribute("errorType").equals("MongoDB")) {
			pw.print("<h4 style='color:red' align='center'>MongoDB is not running!</h4>");
		} else if (request.getAttribute("errorType").equals("MySQL")) {
			pw.print("<h4 style='color:red' align='center'>MySQL is not running!</h4>");
		} else if (request.getAttribute("errorType").equals("Search")) {
			pw.print("<h4 style='color:red' align='center'>An error occured while performing the search!</h4>");
		} else {
			pw.print("<h4 style='color:red' align='center'>Error type unknown!</h4>");
		}
		pw.print("<form action='Home' method='get'><input type='submit' value='Back to Homepage' class='btnno' /></form>");
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}
}

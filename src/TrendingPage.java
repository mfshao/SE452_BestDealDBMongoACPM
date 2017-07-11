import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoTimeoutException;

@WebServlet("/TrendingPage")

public class TrendingPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Trending</a>");
		pw.print("</h2><div class='entry'>");

		pw.print("<h3 align='center'><b>Top Five Most Liked Products</b></h3>");
		ArrayList<Entry<String, Double>> resultList1 = new ArrayList<>();
		try {
			resultList1 = MongoDBDataStoreUtilities.getTopFiveMostLikedProducts();
		} catch (MongoTimeoutException mte) {
			RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
			request.setAttribute("errorType", "MongoDB");
			rd.forward(request, response);
		}

		if (resultList1.isEmpty()) {
			pw.print("<h4 style='color:red'>No data available</h4>");
		} else {
			pw.print("<table cellpadding='5' cellspacing='10' align='center'>");
			pw.print(
					"<tr><td align='center'><b>Product Name</b></td><td align='center'><b>Average Rating</b></td></tr>");
			for (Entry<String, Double> entry : resultList1) {
				pw.print("<tr><td align='center'>" + entry.getKey() + "</td><td align='center'>" + entry.getValue()
						+ "</td></tr>");
			}
			pw.print("</table>");
		}
		pw.print("<br/>");

		pw.print("<h3 align='center'><b>Top Five Zipcodes Where The Most Of Products Sold</b></h3>");
		ArrayList<Entry<LinkedList<String>, Integer>> resultList2 = new ArrayList<>();
		try {
			resultList2 = MongoDBDataStoreUtilities.getTopFiveMostSoldZipcodes();
		} catch (MongoTimeoutException mte) {
			RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
			request.setAttribute("errorType", "MongoDB");
			rd.forward(request, response);
		}
		pw.print("<br/>");
		if (resultList2.isEmpty()) {
			pw.print("<h4 style='color:red'>No data available</h4>");
		} else {
			pw.print("<table cellpadding='5' cellspacing='10' align='center'>");
			pw.print(
					"<tr><td align='center'><b>Zipcode</b></td><td align='center'><b>City</b></td><td align='center'><b>State</b></td><td align='center'><b>Count</b></td></tr>");
			for (Entry<LinkedList<String>, Integer> entry : resultList2) {
				LinkedList<String> ll = entry.getKey();
				pw.print("<tr><td align='center'>" + ll.get(0) + "</td><td align='center'>" + ll.get(1)
						+ "</td><td align='center'>" + ll.get(2) + "</td><td align='center'>" + entry.getValue()
						+ "</td></tr>");
			}
			pw.print("</table>");
		}
		pw.print("<br/>");

		pw.print("<h3 align='center'><b>Top Five Most Reviewed Products</b></h3>");
		ArrayList<Entry<String, Integer>> resultList3 = new ArrayList<>();
		try {
			resultList3 = MongoDBDataStoreUtilities.getTopFiveMostSoldProducts();
		} catch (MongoTimeoutException mte) {
			RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
			request.setAttribute("errorType", "MongoDB");
			rd.forward(request, response);
		}
		pw.print("<br/>");
		if (resultList3.isEmpty()) {
			pw.print("<h4 style='color:red'>No data available</h4>");
		} else {
			pw.print("<table cellpadding='5' cellspacing='10' align='center'>");
			pw.print("<tr><td align='center'><b>Product Name</b></td><td align='center'><b>Number of Reviews</b></td></tr>");
			for (Entry<String, Integer> entry : resultList3) {
				pw.print("<tr><td align='center'>" + entry.getKey() + "</td><td align='center'>" + entry.getValue()
						+ "</td></tr>");
			}
			pw.print("</table>");
		}
		pw.print("<br/>");
		pw.print("<br/>");

		pw.print(
				"<form action='Home' method='get'><input type='submit' value='Back to Homepage' class='btnno' /></form>");

		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

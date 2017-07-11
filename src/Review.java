import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.MongoTimeoutException;

@WebServlet("/Review")

public class Review extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter();
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		if (request.getParameter("Review") != null && request.getParameter("Review").equals("WriteReview")) {
			writeReview(request, response, false, false, false, false);
		} else if (request.getParameter("Review") != null && request.getParameter("Review").equals("SaveReview")) {
			String name = request.getParameter("name");
			String type = request.getParameter("type");

			Product p = utility.getProductByNameAndType(name, type);

			if (p == null) {
				writeReview(request, response, true, false, false, false);
				return;
			}
			String retailerZip = request.getParameter("retailerZip");
			String retailerCity = request.getParameter("retailerCity");
			String retailerState = request.getParameter("retailerState");
			String userName = request.getParameter("userName");
			String sUserAge = request.getParameter("userAge");
			String userGender = request.getParameter("userGender");
			String userOccupation = request.getParameter("userOccupation");
			String sReviewRating = request.getParameter("reviewRating");
			String sReviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");
			int userAge = 0;
			int reviewRating = 0;
			String reviewDate = null;

			if (sUserAge.isEmpty() || userOccupation.isEmpty() || sReviewDate.isEmpty() || reviewText.isEmpty()) {
				writeReview(request, response, false, true, false, false);
				return;
			}

			try {
				userAge = Integer.parseInt(sUserAge);
				reviewRating = Integer.parseInt(sReviewRating);
			} catch (NumberFormatException nfe) {
				writeReview(request, response, false, false, true, false);
				return;
			}

			if (userAge < 1 || userAge > 150) {
				writeReview(request, response, false, false, true, false);
				return;
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			try {
				reviewDate = dateFormat.format(dateFormat.parse(sReviewDate));
			} catch (ParseException pe) {
				writeReview(request, response, false, false, false, true);
				return;
			}

			ItemReview itemReview = new ItemReview(p, retailerZip, retailerCity, retailerState, userName, userAge,
					userGender, userOccupation, reviewRating, reviewDate, reviewText);

			try {
				MongoDBDataStoreUtilities.insertItemReview(itemReview);
			} catch (MongoTimeoutException mte) {
				RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
				request.setAttribute("errorType", "MongoDB");
				rd.forward(request, response);
			}

			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Review Saved</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h2>Your review for " + p.getName() + " has been saved. Thank you!</h2>");
			pw.print("<br/>");
			pw.print(
					"<form action='Home' method='get'><input type='submit' value='Back to Homepage' class='btnno' /></form>");
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");
		} else if (request.getParameter("Review") != null && request.getParameter("Review").equals("ViewReview")) {
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			Product p = utility.getProductByNameAndType(name, type);
			ArrayList<ItemReview> itemReviewList = new ArrayList<>();
			try {
				itemReviewList = MongoDBDataStoreUtilities.getItemReviewsByProduct(p);
			} catch (MongoTimeoutException mte) {
				RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
				request.setAttribute("errorType", "MongoDB");
				rd.forward(request, response);
			}

			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Reviews Received for " + p.getName() + "</a>");
			pw.print("</h2><div class='entry'>");

			if (itemReviewList.isEmpty()) {
				pw.print("<h4 style='color:red'>No reviews available for this product!</h4>");
			} else {
				int i = 1;
				for (ItemReview ir : itemReviewList) {
					pw.print("<table cellpadding='0' cellspacing='5'>");
					pw.print("<tr><td colspan='2'><b>Review #" + i + "</b></td></tr>");
					pw.print("<tr><td>UserName</td><td>" + ir.getUserName() + "</td></tr>");
					pw.print("<tr><td>ReviewRating</td><td>" + ir.getReviewRating() + "</td></tr>");
					pw.print("<tr><td>ReviewDate</td><td>" + ir.getReviewDate() + "</td></tr>");
					pw.print("<tr><td>ReviewText</td><td>" + ir.getReviewText() + "</td></tr>");
					pw.print("<br/>");
					i++;
				}
				pw.print("</table>");
			}
			pw.print(
					"<form><input type='button' value='Back' onClick='history.go(-1);return true;' class='btnno'></form>");
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");
		}
	}

	private void writeReview(HttpServletRequest request, HttpServletResponse response, boolean hasProductError,
			boolean hasEmptyField, boolean hasAgeError, boolean hasDateError) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to write a review");
			response.sendRedirect("Login");
			return;
		}

		String name = request.getParameter("name");
		String type = request.getParameter("type");

		Product p = utility.getProductByNameAndType(name, type);

		if (p == null) {
			hasProductError = true;
		}

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='Review' action='Review' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Write Review</a>");
		pw.print("</h2><div class='entry'>");

		if (hasProductError) {
			pw.print("<h4 style='color:red'>Item parsing error!</h4>");
			pw.print("</div></div></div>");
			pw.print("</form>");
			utility.printHtml("Footer.html");
		} else {
			if (hasEmptyField) {
				pw.print(
						"<h4 style='color:red'>UserAge, UserOccupation, ReviewDate and ReviewText are mandetory!</h4>");
			}

			if (hasAgeError) {
				pw.print("<h4 style='color:red'>UserAge parsing error!</h4>");
			}

			if (hasDateError) {
				pw.print("<h4 style='color:red'>ReviewDate parsing error!</h4>");
			}

			Random rand = new Random(System.currentTimeMillis());
			int index = rand.nextInt(Properties.DEFAULT_RETAILER_ZIP.length);
			pw.print("<input type='hidden' name='name' value='" + name + "' />");
			pw.print("<input type='hidden' name='type' value='" + type + "' />");
			pw.print("<table cellpadding='0' cellspacing='5'>");

			pw.print("<tr><td colspan='4'><b>Product Information</b></td></tr>");
			pw.print("<tr><td>ProductName</td><td colspan='3'>" + p.getName()
					+ "</td><input type='hidden' name='productName' value = '" + p.getName() + "' /></tr>");
			pw.print("<tr><td>ProductCategory</td><td colspan='3'>" + type
					+ "</td><input type='hidden' name='productCategory' value = '" + type + "' /></tr>");
			pw.print("<tr><td>ProductPrice</td><td colspan='3'>$" + p.getPrice()
					+ "</td><input type='hidden' name='productPrice' value = '" + p.getPrice() + "' /></tr>");
			if (p.getDiscount() > 0.0) {
				pw.print(
						"<tr><td>ProductOnSale</td><td colspan='3'>Yes</td><input type='hidden' name='productOnSale' value = 'yes' /></tr>");
			} else {
				pw.print(
						"<tr><td>ProductOnSale</td><td colspan='3'>No</td><input type='hidden' name='productOnSale' value = 'no' /></tr>");
			}

			pw.print("<tr><td colspan='4'><b>Retailer Information</b></td></tr>");
			pw.print(
					"<tr><td>RetailerName</td><td colspan='3'>BestDeal</td><input type='hidden' name='retailerName' value = 'BestDeal' /></tr>");
			pw.print("<tr><td>RetailerZip</td><td colspan='3'>" + Properties.DEFAULT_RETAILER_ZIP[index]
					+ "</td><input type='hidden' name='retailerZip' value = '" + Properties.DEFAULT_RETAILER_ZIP[index]
					+ "' /></tr>");
			pw.print("<tr><td>RetailerCity</td><td colspan='3'>" + Properties.DEFAULT_RETAILER_CITY[index]
					+ "</td><input type='hidden' name='retailerCity' value = '"
					+ Properties.DEFAULT_RETAILER_CITY[index] + "' /></tr>");
			pw.print("<tr><td>RetailerState</td><td colspan='3'>" + Properties.DEFAULT_RETAILER_STATE[index]
					+ "</td><input type='hidden' name='retailerState' value = '"
					+ Properties.DEFAULT_RETAILER_STATE[index] + "' /></tr>");

			pw.print("<tr><td colspan='4'><b>Manufacturer Information</b></td></tr>");
			pw.print("<tr><td>ManufacturerName</td><td colspan='3'>" + p.getManufacturer()
					+ "</td><input type='hidden' name='manufacturerName' value = '" + p.getManufacturer()
					+ "' /></tr>");
			pw.print(
					"<tr><td>ManufacturerRebate</td><td colspan='3'>No</td><input type='hidden' name='manufacturerRebate' value = 'no' /></tr>");

			pw.print("<tr><td colspan='4'><b>User Information</b></td></tr>");
			pw.print("<tr><td>UserName</td><td colspan='3'>" + utility.username()
					+ "</td><input type='hidden' name='userName' value = '" + utility.username() + "' /></tr>");
			pw.print("<tr><td>UserAge</td><td colspan='3'><input type='text' name='userAge' size = '20' /></td></tr>");
			pw.print("<tr><td>UserGender</td><td colspan='3'>");
			pw.print("<select name='userGender'>");
			pw.print("<option value='n'>Not Specified</option>");
			pw.print("<option value='m'>Male</option>");
			pw.print("<option value='f'>Female</option>");
			pw.print("</select>");
			pw.print("</td></tr>");
			pw.print(
					"<tr><td>UserOccupation</td><td colspan='3'><input type='text' name='userOccupation' size = '20' /></td></tr>");

			pw.print("<tr><td colspan='4'><b>Review Information</b></td></tr>");
			pw.print("<tr><td>ReviewRating</td><td colspan='3'>");
			pw.print("<select name='reviewRating'>");
			for (int i = 5; i > 0; i--) {
				pw.print("<option value='" + i + "'>" + i + "</option>");
			}
			pw.print("</select>");
			pw.print("</td></tr>");
			pw.print(
					"<tr><td>ReviewDate</td><td colspan='3'><input type='text' name='reviewDate' size = '20' placeholder='YYYY/MM/DD' /></td></tr>");
			pw.print(
					"<tr><td>ReviewText</td><td colspan='3'><textarea name = 'reviewText' cols = '20' rows = '5'></textarea></td></tr>");

			pw.print("<br/>");
			pw.print(
					"<tr><td><input type='button' value='Back' onClick='history.go(-1);return true;' class='btnno'></td><td colspan='3'>");
			pw.print("<input type='submit' name='Review' value='SaveReview' class='btnyes' />");
			pw.print("</td></tr>");

			pw.print("</table>");
			pw.print("</div></div></div>");
			pw.print("</form>");
			utility.printHtml("Footer.html");
		}
	}
}

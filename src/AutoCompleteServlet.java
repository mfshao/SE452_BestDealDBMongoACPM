import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AutoCompleteServlet")

public class AutoCompleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServletContext context;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.context = config.getServletContext();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getParameter("action");
		String targetID = request.getParameter("id");
		StringBuffer sb = new StringBuffer();
		ArrayList<Product> productList = SaxParserDataStore.getAllProducts();	

		if (targetID != null) {
			targetID = targetID.trim().toLowerCase();
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
			request.setAttribute("errorType", "Search");
			rd.forward(request, response);
		}

		boolean productsAdded = false;
		if (action.equals("complete")) {
			if (!targetID.equals("")) {
				for (Product p : productList) {
					String productName = p.getName().toLowerCase();
					
					if (productName.contains(targetID)) {
						sb.append("<product>");
						sb.append("<productID>" + p.getId() + "</productID>");
						sb.append("<productName>" + p.getName() + "</productName>");
						sb.append("</product>");
						productsAdded = true;
					}
				}
			}

			if (productsAdded) {
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("<products>" + sb.toString() + "</products>");
			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		}

		if (action.equals("lookup")) {
			HashMap<String, Product> productMap = SaxParserDataStore.getAllProductsAsHashMap();
			if ((targetID != null) && productMap.containsKey(targetID.trim())) {
				if (targetID.contains("phone")) {
					context.getRequestDispatcher("/SmartPhoneList").forward(request, response);
				} else if (targetID.contains("laptop")) {
					context.getRequestDispatcher("/LaptopList").forward(request, response);
				} else if (targetID.contains("tv")) {
					context.getRequestDispatcher("/TVList").forward(request, response);
				} else if (targetID.contains("tablet")) {
					context.getRequestDispatcher("/TabletList").forward(request, response);
				} else if (targetID.contains("accessory")) {
					context.getRequestDispatcher("/AccessoryList").forward(request, response);
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("ErrorPage");
					request.setAttribute("errorType", "Search");
					rd.forward(request, response);
				}
			}
		}
	}
}

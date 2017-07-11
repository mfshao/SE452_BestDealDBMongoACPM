import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Startup")

public class Startup extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		SaxParserDataStore.addHashmap();

		MySQLDataStoreUtilities.initalizeDatabase();
		MongoDBDataStoreUtilities.initalizeDatabase();
	}
}

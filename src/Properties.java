
public class Properties {
	public static final String TOMCAT_HOME = System.getProperty("catalina.home");
	public static final String WEBAPP_PATH = "\\wtpwebapps";
	public static final String PROJECT_PATH = "\\BestDealDBMongoACPM";
	public static final String PRODUCT_CATALOG_PATH = "\\ProductCatalog.xml";
	public static final String PRICE_MATCH_FILE_PATH = "\\DealMatches.txt";
	public static final int ORDER_PROCESSING_DAYS = 14;
	public static final int DUMMY_ORDER_ID = -1;
	
	public static final String[] DEFAULT_USERNAME = {"aa", "bb", "cc", "dd", "ee"};
	public static final Integer[] DEFAULT_USERAGE = {24, 28, 47, 56, 29};
	public static final String[] DEFAULT_USERGENDER = {"Male", "Female", "Male", "Female", "Male"};
	public static final String[] DEFAULT_OCCUPATION = {"student", "manager", "IT specialist", "retired", "consultant"};
	public static final String[] DEFAULT_REVIEWDATE = {"2017/01/01", "2017/05/13", "2017/03/21", "2016/12/25", "2017/04/18"};
	public static final String[] DEFAULT_POSITIVE_REVIEWTEXT = {"Good product!", "OK for the money", "Great screen quality", "Fast and easy to use", "Like the color!"};
	public static final String[] DEFAULT_NEGATIVE_REVIEWTEXT = {"Need some improvement", "Overpriced!", "Break after a month", "Picture is not very clear", "Sound quality not good"};
	
	public static final String[] DEFAULT_RETAILER_ZIP = {"60604", "19104", "94704", "78758", "45223"};
	public static final String[] DEFAULT_RETAILER_CITY = {"Chicago", "Philadelphia", "Berkeley", "Austin", "Cincinnati"};
	public static final String[] DEFAULT_RETAILER_STATE = {"IL", "PA", "CA", "TX", "OH"};
}

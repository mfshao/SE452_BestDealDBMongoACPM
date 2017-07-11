import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemReview {
	private String productID;
	private String productName;
	private String productCategory;
	private Double productPrice;
	private boolean productOnSale;
	private String retailerName;
	private String retailerZip;
	private String retailerCity;
	private String retailerState;
	private String manufacturerName;
	private boolean manufacturerRebate;
	private String userName;
	private Integer userAge;
	private String userGender;
	private String userOccupation;
	private Integer reviewRating;
	private String reviewDate;
	private String reviewText;
	
	public ItemReview(Product product, String retailerZip, String retailerCity, String retailerState, String userName, int userAge, String userGender, String userOccupation, int reviewRating, String reviewDate, String reviewText){
		this.productID = product.getId();
		this.productName = product.getName();
		this.productCategory = product.getCategory();
		this.productPrice = product.getPrice();
		if (product.getDiscount() == 0.0) {
			this.productOnSale = false;
		} else {
			this.productOnSale = true;
		}
		this.retailerName = "BestDeal";
		this.retailerZip = retailerZip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.manufacturerName = product.getManufacturer();
		this.manufacturerRebate = false;
		this.userName = userName;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userOccupation = userOccupation;
		this.reviewRating = reviewRating;
		this.reviewDate = reviewDate;
		this.reviewText = reviewText;
	}

	public String getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public String getRetailerZip() {
		return retailerZip;
	}

	public String getRetailerCity() {
		return retailerCity;
	}

	public String getRetailerState() {
		return retailerState;
	}

	public boolean isProductOnSale() {
		return productOnSale;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public boolean isManufacturerRebate() {
		return manufacturerRebate;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public String getUserOccupation() {
		return userOccupation;
	}

	public Integer getReviewRating() {
		return reviewRating;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public String getReviewText() {
		return reviewText;
	}
	
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("productID", productID);
		map.put("productName", productName);
		map.put("productCategory", productCategory);
		map.put("productPrice", productPrice);
		map.put("productOnSale", Boolean.toString(productOnSale));
		map.put("retailerName", retailerName);
		map.put("retailerZip", retailerZip);
		map.put("retailerCity", retailerCity);
		map.put("retailerState", retailerState);
		map.put("manufacturerName", manufacturerName);
		map.put("manufacturerRebate", Boolean.toString(manufacturerRebate));
		map.put("userName", userName);
		map.put("userAge", userAge);
		map.put("userGender", userGender);
		map.put("userOccupation", userOccupation);
		map.put("reviewRating", reviewRating);
		map.put("reviewDate", reviewDate);
		map.put("reviewText", reviewText);
		return map;
	}
	
	public static ItemReview generateRandomReview(Product p) {
		Random rand = new Random(System.currentTimeMillis());
		int index = rand.nextInt(Properties.DEFAULT_USERNAME.length);
		int reviewRating = rand.nextInt(5) + 1;
		String reviewText = "";
		if (reviewRating < 3) {
			reviewText = Properties.DEFAULT_NEGATIVE_REVIEWTEXT[rand.nextInt(Properties.DEFAULT_NEGATIVE_REVIEWTEXT.length)];
		} else {
			reviewText = Properties.DEFAULT_POSITIVE_REVIEWTEXT[rand.nextInt(Properties.DEFAULT_POSITIVE_REVIEWTEXT.length)];
		}
		
		ItemReview itemReview = new ItemReview(p, Properties.DEFAULT_RETAILER_ZIP[index], Properties.DEFAULT_RETAILER_CITY[index], Properties.DEFAULT_RETAILER_STATE[index], Properties.DEFAULT_USERNAME[index], Properties.DEFAULT_USERAGE[index], Properties.DEFAULT_USERGENDER[index], Properties.DEFAULT_OCCUPATION[index], reviewRating, Properties.DEFAULT_REVIEWDATE[rand.nextInt(Properties.DEFAULT_REVIEWDATE.length)], reviewText);
		
		return itemReview;
	}
	
}

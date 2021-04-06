package org.springboot.storelocator.constants;

public class StoreLocatorConstants {

	private StoreLocatorConstants() {

	}

	public static final String ROOT_PATH = "/";

	public static final String STORE_PATH = "/stores";
	public static final String STORE_ID_PATH = "/stores/{storeId}";
	
	public static final String SWAGGER_URL = "/swagger-ui.html";
	public static final String STORE_DATA = "/data/store-data.json";

	public static final String POST_SUCCESS = " Stores Created Succesfully";
	public static final String DELETE_SUCCESS = " Deleted Succesfully";
	public static final String UPDATE_SUCCESS = " Updated Succesfully";

	public static final String INVALID_JSON = "Invalid JSON";
	public static final String DUPLICATE_STORE = "Store ID already Exists :";

	public static final String FILTER_CITY = "city";
	public static final String FILTER_COUNTRY = "country";
	public static final String FILTER_RADIUS = "radius";
	public static final String FILTER_CURRENT = "current";
	public static final String FILTER_LOCATION = "location";
	public static final String FILTER_LATITUDE = "latitude";
	public static final String FILTER_LONGITUDE = "longitude";
	public static final String FILTER_UOM = "uom";
	public static final String KMS = "Kms";

	public static final String GMAP_KEY = "AIzaSyAfGrHRaFjIYIIiWOdaGGTb_EkHavcK47w";

	public static final String MESSAGE = "message";

}

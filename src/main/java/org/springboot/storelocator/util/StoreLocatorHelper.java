package org.springboot.storelocator.util;

import java.util.logging.Logger;

import org.json.JSONObject;
import org.springboot.storelocator.constants.StoreLocatorConstants;

import org.springboot.storelocator.model.StoreLocatorErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import com.app.exception.common.ApiError;



public class StoreLocatorHelper {
	
	private static final String CLASSNAME = StoreLocatorHelper.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	
	public static ResponseEntity<Object> handleStoreException(String errMessage,HttpStatus status) {
		final String methodName = "handleStoreException";
		LOGGER.entering(CLASSNAME, methodName);

		StoreLocatorErrorResponse errorResponse = new StoreLocatorErrorResponse();
		errorResponse.addError(errMessage, status.toString());

		LOGGER.exiting(CLASSNAME, methodName);
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	public static String getSimpleJsonResponseString(String msg) {
		String methodName = "getSimpleJsonResponseString";
		LOGGER.entering(CLASSNAME, methodName);

		LOGGER.exiting(CLASSNAME, methodName);
		return new JSONObject().put(StoreLocatorConstants.MESSAGE, msg).toString();
	}
	
	public static Double getDistance(double latitude1, double longitude1, double latitude2, double longitude2,String uom) {
		String methodName = "getDistance";
		LOGGER.entering(CLASSNAME, methodName);

		double earth_radius = 6371d;
		//Earth radius in miles
		if ("miles".equalsIgnoreCase(uom))
			earth_radius =3963d;

		double dLat = Math.toRadians(latitude2 - latitude1);
		double dLon = Math.toRadians(longitude2 - longitude1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude1))
				* Math.cos(Math.toRadians(latitude2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double d = earth_radius * c;

		LOGGER.exiting(CLASSNAME, methodName);

		return d;
	}
	

}
package org.springboot.storelocator.util;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.google.maps.model.LatLng;
import org.springboot.storelocator.constants.StoreLocatorConstants;
import org.springboot.storelocator.model.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class StoreQueryBuilder {
	public static final String CLASSNAME = StoreQueryBuilder.class.getName();
	public static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	@Autowired
	LocationUtil locationUtil;
	
	/**
	 * 
	 * Method to build query object
	 * 	
	 */
	public Stores buildQuery(MultiValueMap<String, String> queryParams,Stores stores) {
		String methodName = "buildQuery";
		LOGGER.entering(CLASSNAME, methodName);

		double queryLat = 0.0d;
		double queryLng = 0.0d;

		if (queryParams.containsKey(StoreLocatorConstants.FILTER_LOCATION)) {
			String loc = queryParams.getFirst(StoreLocatorConstants.FILTER_LOCATION);
			LatLng coordinates = locationUtil.fetchCoordinatesByLocation(loc,
					StoreLocatorConstants.GMAP_KEY);

			queryLat = coordinates.lat;
			queryLng = coordinates.lng;

		}

		final double qLat = queryLat;
		final double qLng = queryLng;

				if ((queryParams.containsKey(StoreLocatorConstants.FILTER_LATITUDE) && queryParams.containsKey(StoreLocatorConstants.FILTER_LONGITUDE))
						|| (queryParams.containsKey(StoreLocatorConstants.FILTER_LOCATION))) {
					double indexLatitude;
					double indexLongitude;
					if (queryParams.containsKey(StoreLocatorConstants.FILTER_LOCATION)) {
						indexLatitude = qLat;
						indexLongitude = qLng;
					} else {
						indexLatitude = Double.parseDouble(queryParams.getFirst(StoreLocatorConstants.FILTER_LATITUDE));
						indexLongitude = Double.parseDouble(queryParams.getFirst(StoreLocatorConstants.FILTER_LONGITUDE));
					}

					
					stores.setStores(stores.getStores().stream().filter( st -> {
					double latitude = Double.parseDouble(st.getLocation().getLat());
					double longitude = Double.parseDouble(st.getLocation().getLng());
					double radius = 100.0d;
					if (null != queryParams.getFirst(StoreLocatorConstants.FILTER_RADIUS)) {
						radius = Double.parseDouble(queryParams.getFirst(StoreLocatorConstants.FILTER_RADIUS));
					}
					String uom = StoreLocatorConstants.KMS;
					if (null != queryParams.getFirst(StoreLocatorConstants.FILTER_UOM)) {
						uom = queryParams.getFirst(StoreLocatorConstants.FILTER_UOM);
					}

					double distance = StoreLocatorHelper.getDistance(latitude, longitude, indexLatitude,
							indexLongitude,uom);
					LOGGER.log(Level.FINEST, "Distance between {0},{1} and {2},{3} is {4} "+ uom,
							new Object[] { latitude, longitude, indexLatitude, indexLongitude, distance });
					if (distance > radius) {
						return false;
					}else
						return true;
					}).collect(Collectors.toList()));

				
		
		
	}
				
				LOGGER.exiting(CLASSNAME, methodName);
				return stores;

	}
}
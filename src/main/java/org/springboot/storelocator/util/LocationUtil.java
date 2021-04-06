package org.springboot.storelocator.util;

import java.util.logging.Logger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;


@Component
@EnableCaching
public class LocationUtil {
	public static final String CLASSNAME = LocationUtil.class.getName();
	public static final Logger LOGGER = Logger.getLogger(CLASSNAME);



	@Cacheable("storelocator")
	public LatLng fetchCoordinatesByLocation(String loc, String gmapKey) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(gmapKey)
					.build();
			GeocodingResult[] results = null;
			
			LatLng coordinates = null;
			try {
				results = GeocodingApi.geocode(context, loc).await();

				coordinates = results[0].geometry.location;
				LOGGER.info(String.format("Coordinates for location %s are : %s , %s", loc, coordinates.lat, coordinates.lng));
			} catch (Exception e) {
				LOGGER.warning("Error while finding coordinates for " + loc);
			}

		return coordinates;
	}
}

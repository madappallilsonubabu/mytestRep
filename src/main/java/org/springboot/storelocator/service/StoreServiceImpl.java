package org.springboot.storelocator.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springboot.storelocator.constants.StoreLocatorConstants;
import org.springboot.storelocator.model.Store;
import org.springboot.storelocator.model.Stores;
import org.springboot.storelocator.util.StoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Rajesh S Nair
 *
 */

@Service
//@EnableCaching
public class StoreServiceImpl implements StoreService {

	private static final String CLASSNAME = StoreServiceImpl.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	/*
	 * @Value("${store.data-file}") String resourceFile;
	 */
	
	@Value("classpath:/data/store-data.json")
	Resource resourceData;
	
	@Autowired
	StoreQueryBuilder storeQueryBuilder;
	

	@Override
	public boolean saveStores(Stores newStores) throws IOException {
		
		String methodName = "saveStore";
		LOGGER.entering(CLASSNAME, methodName);
		List<Store> storesList;
		Stores currentStores = new Stores();

		ObjectMapper mapper = new ObjectMapper();
		
		
		//currentStores = mapper.readValue(Paths.get(resourceFile).toFile(), Stores.class);
		currentStores = mapper.readValue(resourceData.getInputStream(), Stores.class);
		
		
		storesList = currentStores.getStores();

		storesList.addAll(newStores.getStores());

		currentStores.setStores(storesList);

		//mapper.writeValue(Paths.get(resourceFile).toFile(), currentStores);
		mapper.writeValue(new File(resourceData.getURL().getPath()), currentStores);

		LOGGER.exiting(CLASSNAME, methodName);
		return true;

	}

	@Override
	//@Cacheable(value="store", key="#storeId")
	/**
	 * 
	 */
	public Store findStoreById(int storeId) throws IOException {
		String methodName = "findStoreById";
		LOGGER.entering(CLASSNAME, methodName);
		Stores stores = new Stores();
		ObjectMapper mapper = new ObjectMapper();
		stores = mapper.readValue(resourceData.getInputStream(), Stores.class);

		Optional<Store> store = stores.getStores().stream().filter(st -> st.getStoreId().equals(storeId)).findFirst();

		if (store.isPresent()) {
			LOGGER.exiting(CLASSNAME, methodName);
			return store.get();
		} else {
			LOGGER.exiting(CLASSNAME, methodName);
			return null;
		}
	}

	@Override
	public void deleteStore(Store store) throws IOException {
		String methodName = "deleteStore";
		LOGGER.entering(CLASSNAME, methodName);
		Stores stores = new Stores();
		ObjectMapper mapper = new ObjectMapper();
		stores = mapper.readValue(resourceData.getInputStream(), Stores.class);
		//stores = mapper.readValue(resourceData.getInputStream(), Stores.class);
		stores.getStores().removeIf(st -> st.getStoreId().equals(store.getStoreId()));
		mapper.writeValue(new File(resourceData.getURL().getPath()), stores);
		LOGGER.exiting(CLASSNAME, methodName);

	}

	@Override
	public void updateStore(Store store) throws IOException {
		String methodName = "updateStore";
		LOGGER.entering(CLASSNAME, methodName);
		Stores stores = new Stores();
		ObjectMapper mapper = new ObjectMapper();
		//stores = mapper.readValue(Paths.get(resourceFile).toFile(), Stores.class);
		stores = mapper.readValue(resourceData.getFile(), Stores.class);
		stores.setStores(stores.getStores().stream().map(st -> st.getStoreId().equals(store.getStoreId()) ? store : st)
				.collect(Collectors.toList()));

		mapper.writeValue(new File(resourceData.getURL().getPath()), stores);
		LOGGER.exiting(CLASSNAME, methodName);
	}

	@Override
	//@Cacheable(value="stores", key="#queryParams")
	public Stores getStores(MultiValueMap<String, String> queryParams) throws IOException {

		String methodName = "getStores";
		LOGGER.entering(CLASSNAME, methodName);
		System.out.println("in Service -->"+queryParams);
		Stores stores = new Stores();
		ObjectMapper mapper = new ObjectMapper();
		//stores = mapper.readValue(Paths.get(resourceFile).toFile(), Stores.class);
		stores = mapper.readValue(resourceData.getFile(), Stores.class);
		String city;
		String country;
		String current;

		if (queryParams.containsKey(StoreLocatorConstants.FILTER_CITY)) {
			city = queryParams.getFirst(StoreLocatorConstants.FILTER_CITY);
			stores.setStores(stores.getStores().stream().filter(st -> st.getCity().equalsIgnoreCase(city))
					.collect(Collectors.toList()));

		} else if (queryParams.containsKey(StoreLocatorConstants.FILTER_COUNTRY)) {
			country = queryParams.getFirst(StoreLocatorConstants.FILTER_COUNTRY);
			stores.setStores(stores.getStores().stream().filter(st -> st.getCountry().equalsIgnoreCase(country))
					.collect(Collectors.toList()));
		}

		else if (((queryParams.containsKey(StoreLocatorConstants.FILTER_LATITUDE)
				&& queryParams.containsKey(StoreLocatorConstants.FILTER_LONGITUDE))
				|| (queryParams.containsKey(StoreLocatorConstants.FILTER_LOCATION)))
				&& queryParams.containsKey(StoreLocatorConstants.FILTER_RADIUS)) {

			stores = storeQueryBuilder.buildQuery(queryParams, stores);

		}

		else if (queryParams.containsKey(StoreLocatorConstants.FILTER_CURRENT)) {
			current = queryParams.getFirst(StoreLocatorConstants.FILTER_CURRENT);
			if (current.equalsIgnoreCase("true")) {

				LocalDate localDate = LocalDate.now();
				java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();

				String currentDay = dayOfWeek.toString().substring(0, 3);

				stores.setStores(stores.getStores().stream().filter(st -> {
					for (String open : st.getStlocattr().getOpeninghours().split("~~")) {
						if (!open.contains("CLOSED"))
							if (open.contains(currentDay)) {
								LOGGER.info("CURRENT DAY & TIME--->" + currentDay + " " + LocalTime.now());
								LOGGER.info("RANGE TIME --->" + LocalTime.parse(open.substring(10, 15)) + " "
										+ LocalTime.parse(open.substring(4, 9)));
								if (LocalTime.now().isBefore(LocalTime.parse(open.substring(10, 15)))
										&& LocalTime.now().isAfter(LocalTime.parse(open.substring(4, 9))))

									return true;
							}
					}
					return false;

				}).collect(Collectors.toList()));

			}

		}

		
		LOGGER.exiting(CLASSNAME, methodName);
		return stores;
	}

}
package org.springboot.storelocator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springboot.storelocator.model.Location;
import org.springboot.storelocator.model.Stlocattr;
import org.springboot.storelocator.model.Store;
import org.springboot.storelocator.model.Stores;
import org.springboot.storelocator.service.ApplicationTestConfig;
import org.springboot.storelocator.service.StoreService;
import org.springboot.storelocator.service.StoreServiceImpl;
import org.springboot.storelocator.util.StoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
@WebAppConfiguration
class StoreLocatorControllerTest {

	@InjectMocks
	StoreLocatorController storeLocatorController;

	private MockMvc mockMvc;

	@Mock
	StoreServiceImpl storeService;
	
	@Autowired
	StoreQueryBuilder storeQueryBuilder;

	Stores stores;
	
	Stores storesTemp;

	private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

	// private List<Store> owners;

	@BeforeEach
	public void initStores() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(storeLocatorController).build();
		List<Store> storeList = new ArrayList<Store>();
		stores = new Stores();

		Store testStore = new Store();

		testStore.setStoreId(100);
		testStore.setStoreName("Store Marvel");
		testStore.setCity("New York");
		testStore.setCountry("USA");
		testStore.setCountrycode("US");
		testStore.setPhone("+1 2346453245");
		testStore.setZipcode("9877");
		testStore.setAddress1("32,26 avenue,NY");
		Location location = new Location();
		location.setLat("40.22");
		location.setLng("60.34");
		Stlocattr stlocattr = new Stlocattr();
		stlocattr.setLanguageId(100);
		stlocattr.setOpeninghours(
				"MON 09:00-21:00~~TUE 09:00-21:00~~WED 09:00-21:00~~THU 09:00-21:00~~FRI 09:00-21:00~~SAT 09:00-21:00~~SUN 09:00-21:00");
		testStore.setLocation(location);
		testStore.setStlocattr(stlocattr);
		storeList.add(testStore);

		testStore = new Store();
		testStore.setStoreId(200);
		testStore.setStoreName("Store DC");
		testStore.setCity("London");
		testStore.setCountry("Great Britain");
		testStore.setCountrycode("GB");
		testStore.setPhone("+2353245");
		testStore.setZipcode("10298");
		testStore.setAddress1("32,sl road,GB");
		location = new Location();
		location.setLat("20.12");
		location.setLng("30.34");
		stlocattr = new Stlocattr();
		stlocattr.setLanguageId(200);
		stlocattr.setOpeninghours(
				"MON 09:00-21:00~~TUE 09:00-21:00~~WED 09:00-21:00~~THU 09:00-21:00~~FRI 09:00-21:00~~SAT 09:00-21:00~~SUN 09:00-21:00");
		testStore.setLocation(location);
		testStore.setStlocattr(stlocattr);
		storeList.add(testStore);

		testStore = new Store();
		testStore.setStoreId(300);
		testStore.setStoreName("Store Orelly");
		testStore.setCity("Amsterdam");
		testStore.setCountry("Netherland");
		testStore.setCountrycode("NL");
		testStore.setPhone("+4353245");
		testStore.setZipcode("105698");
		testStore.setAddress1("30,vm post,NL");
		location = new Location();
		location.setLat("90.12");
		location.setLng("88.34");
		stlocattr = new Stlocattr();
		stlocattr.setLanguageId(300);
		stlocattr.setOpeninghours(
				"MON 09:00-21:00~~TUE 09:00-21:00~~WED 09:00-21:00~~THU 09:00-21:00~~FRI 09:00-21:00~~SAT 09:00-21:00~~SUN 09:00-21:00");
		testStore.setLocation(location);
		testStore.setStlocattr(stlocattr);
		storeList.add(testStore);

		stores.setStores(storeList);

	}

	@Test
	public void testGetAllStores() throws Exception {
	
		//given(this.storeService.getStores(queryParams)).willReturn(stores);
		 when(this.storeService.getStores(queryParams)).thenReturn(stores);

		
		 ResponseEntity<Object> response =
		 storeLocatorController.getStores(queryParams); Stores storesData = (Stores)
		 response.getBody(); assertNotNull(storesData); assertEquals(stores,
		 storesData); assertEquals(HttpStatus.OK, response.getStatusCode());
		 

		/*
		 * this.mockMvc.perform(get("/stores") .accept(MediaType.APPLICATION_JSON))
		 * .andExpect(status().isOk()) //
		 * .andExpect(content().contentType("application/json"))
		 * .andExpect(jsonPath("$.[0].storeId").value(100))
		 * .andExpect(jsonPath("$.[0].storeName").value("Store Marvel"))
		 * .andExpect(jsonPath("$.[1].storeId").value(200))
		 * .andExpect(jsonPath("$.[1].storeName").value("Store DC"));
		 */

	}

	
	@Test
	public void testGetStoresByCity() throws Exception {
		
		queryParams.set("city", "Newkf");

		when(this.storeService.getStores(queryParams)).thenReturn(stores);

		ResponseEntity<Object> response = storeLocatorController.getStores(queryParams);
		Stores storesData = (Stores) response.getBody();
		assertNotNull(storesData);
		
		stores.setStores(stores.getStores().stream()
				.filter(st -> st.getCity().equals("Amsterdam"))
				.collect(Collectors.toList()));
		
		assertEquals(stores.getStores().get(0).getCity(), storesData.getStores().get(0).getCity());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	 

	@Test
	public void testGetStoresByCountry() throws Exception {
		
		queryParams.set("country", "USA");

		when(this.storeService.getStores(queryParams)).thenReturn(stores);

		ResponseEntity<Object> response = storeLocatorController.getStores(queryParams);
		Stores storesData = (Stores) response.getBody();
		assertNotNull(storesData);
		
		stores.setStores(stores.getStores().stream()
				.filter(st -> st.getCountry().equals("USA"))
				.collect(Collectors.toList()));
		
		assertEquals(stores.getStores().get(0).getCountry(), storesData.getStores().get(0).getCountry());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testGetStoresByRadius() throws Exception {
		
		queryParams.set("radius", "100");
		queryParams.set("longitude", "50.44");
		queryParams.set("latitude", "44.23");
		
		when(this.storeService.getStores(queryParams)).thenReturn(stores);

		ResponseEntity<Object> response = storeLocatorController.getStores(queryParams);
		Stores storesData = (Stores) response.getBody();
		assertNotNull(storesData);
		
		stores = storeQueryBuilder.buildQuery(queryParams, stores);

		if(!stores.getStores().isEmpty() && !storesData.getStores().isEmpty())
			assertEquals(stores.getStores().get(0).getStoreId(), storesData.getStores().get(0).getStoreId());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testGetStoresCurrent() throws Exception {
		
		queryParams.set("current", "true");

		when(this.storeService.getStores(queryParams)).thenReturn(stores);

		ResponseEntity<Object> response = storeLocatorController.getStores(queryParams);
		Stores storesData = (Stores) response.getBody();
		assertNotNull(storesData);
		LocalDate localDate = LocalDate.now();
		java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		String currentDay = dayOfWeek.toString().substring(0, 3);
		stores.setStores(stores.getStores().stream().filter(st -> {
			for (String open : st.getStlocattr().getOpeninghours().split("~~")) {
				
				if (!open.contains("CLOSED"))
					
					if (open.contains(currentDay)) {
						
						if (LocalTime.now().isBefore(LocalTime.parse(open.substring(10, 15)))
								&& LocalTime.now().isAfter(LocalTime.parse(open.substring(4, 9))))

							return true;
					}
			}
			return false;

		}).collect(Collectors.toList()));
		
		assertEquals(stores.getStores().get(0).getStoreId(), storesData.getStores().get(0).getStoreId());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
}


package org.springboot.storelocator.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springboot.storelocator.constants.StoreLocatorConstants;
import org.springboot.storelocator.exception.StoreConfigurationException;
import org.springboot.storelocator.exception.StoreServiceException;
import org.springboot.storelocator.model.Store;
import org.springboot.storelocator.model.Stores;
import org.springboot.storelocator.service.StoreService;
import org.springboot.storelocator.util.StoreLocatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * Controller Class for Store Locator Application
 * 
 * @author Rajesh S Nair
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping(StoreLocatorConstants.ROOT_PATH)
public class StoreLocatorController {

	private static final String CLASSNAME = StoreLocatorController.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	@Autowired
	private StoreService storeService;

	@Value("#{servletContext.contextPath}")
	private String servletContextPath;

	
	@GetMapping(value = StoreLocatorConstants.STORE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getStores(@RequestParam(required = false) MultiValueMap<String, String> queryParams) {
		String methodName = "getStores";
		LOGGER.entering(CLASSNAME, methodName);
		Stores storeResults = null;
		try {

			storeResults = storeService.getStores(queryParams);

		} catch (StoreConfigurationException configException) {
			return StoreLocatorHelper.handleStoreException(configException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (StoreServiceException serviceException) {
			return StoreLocatorHelper.handleStoreException(serviceException.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return StoreLocatorHelper.handleStoreException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.exiting(CLASSNAME, methodName);

		return ResponseEntity.ok().body(storeResults);
	}

	@PutMapping(value = StoreLocatorConstants.STORE_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateStore(@PathVariable("storeId") int storeId, @RequestBody @Valid Store store,
			BindingResult bindingResult) {
		String methodName = "updateStore";
		LOGGER.entering(CLASSNAME, methodName);

		try {

			Store currentStore = storeService.findStoreById(storeId);
			if (currentStore == null) {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}

			if (currentStore.getStoreId().equals(store.getStoreId())) {
				this.storeService.updateStore(store);

				LOGGER.exiting(CLASSNAME, methodName);

				return ResponseEntity.ok().body(StoreLocatorHelper
						.getSimpleJsonResponseString(store.getStoreName() + StoreLocatorConstants.UPDATE_SUCCESS));

			}
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

		} catch (StoreConfigurationException configException) {
			return StoreLocatorHelper.handleStoreException(configException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (StoreServiceException serviceException) {
			return StoreLocatorHelper.handleStoreException(serviceException.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return StoreLocatorHelper.handleStoreException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = StoreLocatorConstants.STORE_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteStore(@PathVariable("storeId") int storeId) {
		String methodName = "deleteStore";
		LOGGER.entering(CLASSNAME, methodName);

		try {

			Store store = storeService.findStoreById(storeId);
			if (store == null) {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

			}
			this.storeService.deleteStore(store);

			LOGGER.exiting(CLASSNAME, methodName);

			// return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			return ResponseEntity.ok().body(StoreLocatorHelper
					.getSimpleJsonResponseString(store.getStoreName() + StoreLocatorConstants.DELETE_SUCCESS));

		} catch (StoreConfigurationException configException) {
			return StoreLocatorHelper.handleStoreException(configException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (StoreServiceException serviceException) {
			return StoreLocatorHelper.handleStoreException(serviceException.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return StoreLocatorHelper.handleStoreException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value = StoreLocatorConstants.STORE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addStore(@RequestBody @Valid Stores stores, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		String methodName = "addStore";
		LOGGER.entering(CLASSNAME, methodName);

		HttpHeaders headers = new HttpHeaders();

		JSONObject response = new JSONObject();
		try {
			if (bindingResult.hasErrors() || stores.getStores().isEmpty()) {
				return StoreLocatorHelper.handleStoreException(StoreLocatorConstants.INVALID_JSON,
						HttpStatus.BAD_REQUEST);
			}

			if (Optional.ofNullable(stores).isPresent()) {

				Optional<Store> currentStore = stores.getStores().stream().filter(st -> {
					try {
						if (storeService.findStoreById(st.getStoreId()) != null)
							return true;

						else
							return false;
					} catch (IOException e) {
						return true;

					}
				}).findFirst();

				if (currentStore.isPresent()) {
					return ResponseEntity.badRequest().body(StoreLocatorHelper.getSimpleJsonResponseString(
							StoreLocatorConstants.DUPLICATE_STORE + currentStore.get().getStoreId()));

				}

				boolean storeSaved = storeService.saveStores(stores);

				response.put("SUCCESS", storeSaved);
				LOGGER.exiting(CLASSNAME, methodName);
				return ResponseEntity.created(ucBuilder.path(StoreLocatorConstants.STORE_PATH).buildAndExpand().toUri())
						.body(StoreLocatorHelper.getSimpleJsonResponseString(
								stores.getStores().size() + StoreLocatorConstants.POST_SUCCESS));

			}

			return new ResponseEntity<Object>(headers, HttpStatus.NO_CONTENT);

		} catch (StoreConfigurationException configException) {
			return StoreLocatorHelper.handleStoreException(configException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (StoreServiceException serviceException) {
			return StoreLocatorHelper.handleStoreException(serviceException.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return StoreLocatorHelper.handleStoreException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

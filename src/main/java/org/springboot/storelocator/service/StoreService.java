package org.springboot.storelocator.service;

import java.io.IOException;
import org.springboot.storelocator.model.Store;
import org.springboot.storelocator.model.Stores;
import org.springframework.util.MultiValueMap;

/**
 * @author Rajesh S Nair
 *
 */

public interface StoreService {
	
	boolean saveStores(Stores store) throws IOException;

	Stores getStores(MultiValueMap<String, String> queryParams) throws IOException;

	Store findStoreById(int storeId) throws IOException;

	void deleteStore(Store store) throws IOException;

	void updateStore(Store store) throws IOException;

	

}

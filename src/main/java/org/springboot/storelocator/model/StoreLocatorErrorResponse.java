package org.springboot.storelocator.model;

import java.util.ArrayList;
import java.util.List;


public class StoreLocatorErrorResponse {

	public List<StoreLocatorError> error = new ArrayList<>();
	
	

	public StoreLocatorErrorResponse() {
		
	}

	public void addError(String message, String code) {
		error.add(new StoreLocatorError(message, code));
	}

	public List<StoreLocatorError> getError() {
		return error;
	}

}

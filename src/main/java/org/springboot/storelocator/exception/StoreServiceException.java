package org.springboot.storelocator.exception;

public class StoreServiceException extends RuntimeException {

    
	private static final long serialVersionUID = 1L;

	public StoreServiceException(String message) {

        super(message);
    }

}

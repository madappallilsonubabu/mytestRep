package org.springboot.storelocator.model;

import java.util.List;


public class Stores {
private List<Store> stores;




public Stores() {
	
}

public Stores(List<Store> stores) {
	
	this.stores = stores;
}

public List<Store> getStores() {
	return stores;
}

public void setStores(List<Store> stores) {
	this.stores = stores;
}



}

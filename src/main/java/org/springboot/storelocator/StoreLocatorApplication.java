package org.springboot.storelocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring Boot Application Class for Store Locator
 *
 * @author Rajesh S Nair
 *
 */


@SpringBootApplication
//@EnableCaching
public class StoreLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreLocatorApplication.class, args);
	}

}

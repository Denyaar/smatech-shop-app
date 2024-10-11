package com.smatech.smatech_shop_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class SmatechShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmatechShopAppApplication.class, args);
	}

}

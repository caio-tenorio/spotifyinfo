package com.spotifyinfo.spotifyclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpotifyclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyclientApplication.class, args);
	}

}

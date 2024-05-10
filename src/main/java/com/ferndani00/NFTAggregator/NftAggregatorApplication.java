package com.ferndani00.NFTAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NftAggregatorApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(NftAggregatorApplication.class, args);
	}

}
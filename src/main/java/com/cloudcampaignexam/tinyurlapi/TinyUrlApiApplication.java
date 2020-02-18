package com.cloudcampaignexam.tinyurlapi;

import com.cloudcampaignexam.tinyurlapi.models.TinyUrl;
import com.cloudcampaignexam.tinyurlapi.utils.InMemoryCache;
import com.cloudcampaignexam.tinyurlapi.utils.TinyUrlHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TinyUrlApiApplication {

	@Bean
	public TinyUrlHelper getTinyUrlHelper(){
		return new TinyUrlHelper();
	}

	@Bean
	public TinyUrlManagementService getTinyUrlManagementService(){
		return new TinyUrlManagementService();
	}

	@Bean
	public InMemoryCache<String,TinyUrl> getTinyUrlCache(){
		//Hardcoded for now no refresh and 1000 max items for now
		return new InMemoryCache<String,TinyUrl>(0, 0, 1000);
	}

	public static void main(String[] args) {
		SpringApplication.run(TinyUrlApiApplication.class, args);
	}

}

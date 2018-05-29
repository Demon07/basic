package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaijCongfiguration {
	@Bean
	public String[] myBean(@Value("${list}") String[] list) {
		for (String i : list) {
			System.out.println(i);
		}
		return list;
	}
}

package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping(value="/xyz")
	public String hello() {
		
		
		return "xyz";
	
	}

}

package com.example.demo.bean;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
@Data
@ConfigurationProperties(prefix = "foo")
public class FooProperties {

	private String username;

	private String tel;

	private String email;

	private Map<String, String> map;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "FooProperties [username=" + username + ", tel=" + tel + ", email=" + email + ", map=" + map + "]";
	}
	
}
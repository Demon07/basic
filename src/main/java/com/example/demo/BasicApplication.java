package com.example.demo;

import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import com.example.demo.bean.FooProperties;
import com.example.demo.bean.TaijCongfiguration;



@SpringBootApplication
public class BasicApplication {
	
	private static final Logger log = LoggerFactory.getLogger(BasicApplication.class);
	
	public static void main(String[] args) {
		 SpringApplication.run(BasicApplication.class, args);
	}
	
	@Autowired
	private FooProperties fooProperties;
	
	@Autowired
	private TaijCongfiguration taijCongfiguration;
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			log.debug("Using log4j2 ...... ?");
			System.out.println();
			System.out.println("CommandLine Runner:");
			for (String arg : args) {
				System.out.println(arg);
			}
			
		};
	}
    @Autowired
    DataSource dataSource;
	@Bean
	public ApplicationRunner appRunner() {
		return args -> {
			System.out.println();
			System.out.println("Application Runner:");
			for (String opt : args.getOptionNames()) {
				System.out.print(opt);
				System.out.print("->");
				System.out.println(args.getOptionValues(opt).stream().collect(Collectors.joining(",", "[", "]")));
				// String.join(",", args.getOptionValues(opt));
			System.out.println("dataSource:"+dataSource);
			System.out.println(taijCongfiguration.toString());
			}
		};
	}

	@Autowired
	private CounterService counterService;

	@Bean
	public ApplicationListener<ApplicationEvent> helloListener() {
		final String HELLO_URL = "/hello";

		return (ApplicationEvent event) -> {
			if (event instanceof ServletRequestHandledEvent) {
				ServletRequestHandledEvent e = (ServletRequestHandledEvent) event;
				if (e.getRequestUrl().equals(HELLO_URL))
					counterService.increment("hello.hits");
			}
		};
	}

	/*@Bean
	public HealthIndicator myHealth() {
		return () -> {
	         
//			return Health.up().build();
			return Health.down().withDetail("Error Code", 404).build();
		};
	}*/
	
	@Bean
	public HealthIndicator myHealth() {
		return () -> {
//			return Health.up().build();
			RestTemplate restTemplate = new RestTemplate();
			try {
				String string = restTemplate.getForObject("https://www.baidu.com", String.class);
				System.out.println(string);
				return Health.up().build();
			} catch (Exception e) {
				return Health.down().withDetail("Error Code", 404).build();
			}
		};
	}
	
	  @Bean
	    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	        PropertySourcesPlaceholderConfigurer propsConfig 
	          = new PropertySourcesPlaceholderConfigurer();
	        propsConfig.setLocation(new ClassPathResource("git.properties"));
	        propsConfig.setIgnoreResourceNotFound(true);
	        propsConfig.setIgnoreUnresolvablePlaceholders(true);
	        return propsConfig;
	    }
}

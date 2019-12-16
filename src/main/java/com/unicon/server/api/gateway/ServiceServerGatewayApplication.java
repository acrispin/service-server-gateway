package com.unicon.server.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *
 * @author acrispin
 * @since 12/12/2019
 */
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class ServiceServerGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceServerGatewayApplication.class, args);
	}

}

package com.device.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.device.business.service.HelloWorld;

public class WebServiceClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-client.xml");
			
		    
		    HelloWorld client = (HelloWorld) ctx.getBean("helloClient");  
		    String result = client.sayHello(" World !");  
		    System.out.println(result);  
	}

}

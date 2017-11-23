package com.device.business.service.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.device.business.service.HelloWorld;

@WebService
public class HelloWorldImpl implements HelloWorld {

	@WebMethod(operationName = "sayHello")
	public String sayHello(@WebParam(name = "arg0") String text) {
		// TODO Auto-generated method stub 
		return "Hello" + text ;  
	}
	
	public static void main(String[] args) {
		/*ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-client.xml");
		
	     
	    HelloWorld client = (HelloWorld) ctx.getBean("helloClient");  */
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://localhost:8080/deviceManager/webservice/helloWorld?WSDL");
        factory.setServiceClass(HelloWorld.class);
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.getOutInterceptors().add(new LoggingInInterceptor());
        HelloWorld client = (HelloWorld) factory.create();
        
	    String result = client.sayHello(" World !");  
	    System.out.println(result);  
}

}

package com.device.business.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface  HelloWorld  {
	@WebMethod(operationName = "sayHello")
	    public String sayHello(@WebParam(name="arg0")String text); 
}

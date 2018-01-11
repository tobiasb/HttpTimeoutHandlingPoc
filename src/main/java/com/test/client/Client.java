package com.test.client;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

public class Client {
	private static String host = "http://localhost:8000/webhook";

    public static void main(String[] args) throws Exception {
    	MultiThreadedHttpConnectionManager connManager = new MultiThreadedHttpConnectionManager();
    	
    	//connection settings similar to prod
    	connManager.getParams().setConnectionTimeout(100); // Timeout while waiting to establish TCP connection with target
    	connManager.getParams().setSoTimeout(500); // Timeout while waiting for response after request has been sent
    	connManager.getParams().setDefaultMaxConnectionsPerHost(10);
    	connManager.getParams().setMaxTotalConnections(100);
    	
    	HttpClient httpClient = new HttpClient(connManager);

    	makeRequest(httpClient, 100);//successful
    	makeRequest(httpClient, 1000);//will time out
    }
    
    private static void makeRequest(HttpClient httpClient, int sleepDuration) {
    	PostMethod postMethod = new PostMethod(host);
    	
    	//tell server to sleep before sending response to simulate long running process
    	postMethod.setRequestHeader("sleepDuration", String.valueOf(sleepDuration));
    	
		try {
			int statusCode = httpClient.executeMethod(postMethod);
	    	System.out.println("The request succeeded with status " + statusCode);
		} catch (IOException e) {
	    	System.out.println("The request timed out");
		}
    }
}

package com.test.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebhookServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/webhook", new SleepingHandler());
        server.setExecutor(null);
        server.start();
    }

    static class SleepingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
        	int duration = Integer.parseInt(t.getRequestHeaders().getFirst("sleepDuration"));
        	System.out.println("Sleep duration: " + duration);

    		Sleep(duration);
        	
            try {
                String response = "This is the response";
				t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
	        	System.out.println("Sending the response succeeded");
			} catch (IOException e) {
	        	System.out.println("Sending the response failed");
			}
        }

		private void Sleep(int sleepMs) {
			try {
				Thread.sleep(sleepMs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
}
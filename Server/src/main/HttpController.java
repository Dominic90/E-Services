package main;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpController {

//	public static String url = "http://localhost"
	
	public HttpController() {
		// TODO Auto-generated constructor stub
	}
	
	public void deleteEvent(String eventId) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://localhost:3000/events/" + eventId + "/server_destroy");
		HttpResponse response;
		try {
			response = httpClient.execute(request);
			System.out.println(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

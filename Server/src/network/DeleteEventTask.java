package network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class DeleteEventTask extends Thread {

	private String eventId;
	
	public DeleteEventTask(String eventId) {
		this.eventId = eventId;
	}
	
	@Override
	public void run() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://localhost/events/" + eventId + "/server_destroy");
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

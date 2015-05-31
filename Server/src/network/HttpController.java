package network;


public class HttpController {

	public HttpController() {
		
	}
	
	public void deleteEvent(String eventId) {
		new DeleteEventTask(eventId).start();
	}
}

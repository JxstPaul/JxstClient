package JxstClient;

import JxstClient.event.EventManager;
import JxstClient.gui.SplashScreen.SplashProgress;

public class Client {

	public static final Client INSTANCE = new Client();
	public String name = "JxstClient", ver = "b1.0.0";
	
	public EventManager eventManager;
	
	public void init() {
		SplashProgress.setPROGRESS(1, "Client - Initializing");
		eventManager = new EventManager();
		
		eventManager.register(this);
	}	
}

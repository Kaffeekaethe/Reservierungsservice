package reservierung.clients;

import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;

public class BuchungsClient {
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static String getInstanceAddress(){
		// Finde einen Buchungsservice
		InstanceInfo nextServerInfo = DiscoveryManager.getInstance().getDiscoveryClient()
				.getNextServerFromEureka("buchungsservice", false);
		System.out.println(String.format("Gefunden: %s, Adresse: %s", nextServerInfo.getAppName(),
				nextServerInfo.getIPAddr()));
		return String.format("http://%s:%s", nextServerInfo.getIPAddr(), nextServerInfo.getPort());
	}
	
	public static String buche(int zug, int platz) {
		try {
			System.out.println("Freie Zuege ermitteln");
			return restTemplate.getForObject(getInstanceAddress() + "/buche?zug={zug}&platz={platz}", String.class, zug,
					platz);
		} catch (Exception e) {
			return "Error" + e.getMessage();
		}
	}

}

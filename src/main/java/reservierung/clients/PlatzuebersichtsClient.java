package reservierung.clients;

import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;

public class PlatzuebersichtsClient {

	private static RestTemplate restTemplate = new RestTemplate();
	
	public static String getInstanceAddress(){
		// Finde einen Buchungsservice
		InstanceInfo nextServerInfo = DiscoveryManager.getInstance().getDiscoveryClient()
				.getNextServerFromEureka("platzuebersichtsservice", false);
		
		//Logging
		System.out.println(String.format("Gefunden: %s, Adresse: %s", nextServerInfo.getAppName(),
				nextServerInfo.getHomePageUrl()));
		
		System.out.println(String.format("Gefunden: %s, Adresse: %s:%s", nextServerInfo.getAppName(),
				nextServerInfo.getIPAddr(), nextServerInfo.getPort()));
		
		return String.format("http://%s:%s", nextServerInfo.getIPAddr(), nextServerInfo.getPort());
	}
	public static String requestZuege(String start, String ziel) {
		try {
			System.out.println("Freie Zuege ermitteln");
			return restTemplate.getForObject(getInstanceAddress() + "/zuege?start={start}&ziel={ziel}", String.class, start, ziel);
		} catch (Exception e) {
			System.out.println("Freie Züge konnten nicht ermittelt werden" + e.getMessage());
			return "Freie Züge konnten nicht ermittelt werden";
		}

	}

	public static String requestPlaetze(int zug) {
		try {
			System.out.println("Freie Zuege ermitteln");
			return restTemplate.getForObject(getInstanceAddress() + "/plaetze?zug={zug}", String.class, zug);
		} catch (Exception e) {
			System.out.println("Freie Plätze konnten nicht ermittelt werden" + e.getMessage());
			return "Freie Plätze konnten nicht ermittelt werden";
		}
	}
	
	public static String requestHaltestellen() {
		try {
			System.out.println("Haltestellen ermitteln");
			return restTemplate.getForObject(getInstanceAddress() + "/haltestellen", String.class);
		} catch (Exception e) {
			System.out.println("Haltetellen konnten nicht ermittelt werden " + e.getMessage());
			return "Haltetellen konnten nicht ermittelt werden";
		}
	}

}

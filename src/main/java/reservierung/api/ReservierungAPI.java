package reservierung.api;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import reservierung.clients.BuchungsClient;
import reservierung.clients.PlatzuebersichtsClient;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableEurekaClient
public class ReservierungAPI {

	@RequestMapping("/")
	public ModelAndView home() {

		ModelAndView model = new ModelAndView("index");
		model.addObject("zuege", "");
		model.addObject("plaetze", "");

		try {
			JSONArray json = new JSONArray(PlatzuebersichtsClient.requestHaltestellen());

			ArrayList<String> s = new ArrayList<String>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject obj = new JSONObject(json.getString(i));
				s.add(obj.getString("Haltestelle"));
			}
			model.addObject("haltestellen", s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addObject("haltestellen", "Haltestellen konnten nicht ermittelt werden");
		}

		return model;
	}

	@RequestMapping("/zuege")
	public String zuege(@RequestParam(value = "start") String start, @RequestParam(value = "ziel") String ziel,
			Model model) {

		return PlatzuebersichtsClient.requestZuege(start, ziel);

	}

	@RequestMapping("/plaetze")
	public String plaetze(@RequestParam(value = "zug") int zug, Model model) {
		/*
		 * try {
		 * 
		 * System.out.println(PlatzuebersichtsClient.requestPlaetze(zug));
		 * 
		 * JSONArray json = new
		 * JSONArray(PlatzuebersichtsClient.requestPlaetze(zug));
		 * 
		 * ArrayList<String> s = new ArrayList<String>(); for (int i = 0; i <
		 * json.length(); i++) { JSONObject obj = new
		 * JSONObject(json.getString(i)); s.add(obj.getString("platzID")); }
		 * model.addAttribute("plaetze", s);
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); model.addAttribute("plaetze", "JSON"); }
		 * model.addAttribute("zuege", "");
		 */
		return PlatzuebersichtsClient.requestPlaetze(zug);
	}

	@RequestMapping("/buche")
	public String buchung(@RequestParam(value = "zug") int zug, @RequestParam(value = "platz") int platz) {
		return BuchungsClient.buche(zug, platz);
	}

}
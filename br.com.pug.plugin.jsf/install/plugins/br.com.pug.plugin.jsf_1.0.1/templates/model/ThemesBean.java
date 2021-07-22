/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageBeanUtil%;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ApplicationScoped
@ManagedBean
public class ThemesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7411202843108522073L;
	private Map<String, String> themes; 

	@PostConstruct  
	public void init() {  
		themes = new TreeMap<String, String>();
		themes.put("Afterdark", "afterdark"); 
		themes.put("Afternoon", "afternoon"); 
		themes.put("Afterwork", "afterwork"); 
		themes.put("Aristo", "aristo"); 
		themes.put("Black-tie", "black-tie");
		themes.put("Blitzer", "blitzer"); 
		themes.put("Bluesky", "bluesky"); 
		themes.put("Bootstrap", "bootstrap"); 
		themes.put("Casablanca", "casablanca");
		themes.put("Cruze", "cruze"); 
		themes.put("Cupertino", "cupertino");
		themes.put("Dark-hive", "dark-hive"); 
		themes.put("Delta", "delta");
		themes.put("Dot-luv", "dot-luv");
		themes.put("Eggplant", "eggplant");
		themes.put("Excite-bike", "excite-bike");
		themes.put("Flick", "flick"); 
		themes.put("Glass-x", "glass-x");
		themes.put("Home", "home"); 
		themes.put("Hot-sneaks", "hot-sneaks");
		themes.put("Humanity", "humanity");
		themes.put("Le-frog", "le-frog");
		themes.put("Midnight", "midnight");
		themes.put("Mint-choc", "mint-choc");
		themes.put("Overcast", "overcast"); 
		themes.put("Pepper-grinder", "pepper-grinder");
		themes.put("Redmond", "redmond"); 
		themes.put("Rocket", "rocket");
		themes.put("Sam", "sam"); 
		themes.put("Smoothness", "smoothness"); 
		themes.put("South-street", "south-street");
		themes.put("Start", "start"); 
		themes.put("Sunny", "sunny"); 
		themes.put("Swanky-purse", "swanky-purse");
		themes.put("Trontastic", "trontastic");
		themes.put("UI-darkness", "ui-darkness");
		themes.put("UI-lightness", "ui-lightness");
		themes.put("Vader", "vader");
	}

	public Map<String, String> getThemes() {
		return themes;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}
	
}

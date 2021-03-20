import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;


public class WeatherForecast {
	public static String area = "Pune";

	public static String getRainForcast() throws JAXBException, IOException {
		YahooWeatherService service = new YahooWeatherService();
		List<Channel> channels = service.getForecastForLocation(area, DegreeUnit.CELSIUS).first(1);
		String Decision_tree=AlgoMain.Algo();
		for (Channel c : channels)
		{
			System.out.println(c.getTitle());
			List<Forecast> forcasts = c.getItem().getForecasts();

			for(Forecast f :forcasts)
			{
				System.out.println(f.getDate() + ":" + f.getText());
				if((Decision_tree.equalsIgnoreCase("Yes"))&&(f.getText().contains("Sunny"))||(EventServlet.Event_decision.equalsIgnoreCase("Y")))
				{
					return "Y";
				}
				else
					return "N";
			}
				
		}			
		return "N";
	}
/*	
	public static void main(String[] args) {
		
		try {
			System.out.println(WeatherForecast.getRainForcast());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
}
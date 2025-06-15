package de.bcxp.challenge;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bcxp.challenge.weather.CsvWeatherDataReader;
import de.bcxp.challenge.weather.WeatherAnalyzer;
import de.bcxp.challenge.weather.WeatherData;

/**
 * The entry class for your solution. This class is only aimed as starting point
 * and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * 
     * @param args The CLI arguments passed
     */
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String... args) {

        CsvWeatherDataReader weatherDataReader = new CsvWeatherDataReader(
                "src/main/resources/de/bcxp/challenge/weather.csv");
        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();

        try {
            List<WeatherData> weatherData = weatherDataReader.read();
            logger.info("Successfully read weather data.");

            String dayWithSmallestTempSpread = weatherAnalyzer.getDayWithSmallestTempSpread(weatherData);
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.printf(e.getMessage());
        }

        String countryWithHighestPopulationDensity = "Some country"; // Your population density analysis function call …
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}

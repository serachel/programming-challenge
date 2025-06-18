package de.bcxp.challenge;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bcxp.challenge.countries.CsvCountryDataReader;
import de.bcxp.challenge.countries.CountryAnalyzer;
import de.bcxp.challenge.countries.CountryData;
import de.bcxp.challenge.countries.CountryDataReader;
import de.bcxp.challenge.countries.CountryDataReaderException;
import de.bcxp.challenge.weather.CsvWeatherDataReader;
import de.bcxp.challenge.weather.WeatherDataReader;
import de.bcxp.challenge.weather.WeatherDataReaderException;
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
        String weatherFilePath = System.getenv("WEATHER_FILE_PATH");
        try {
            if (weatherFilePath == null || weatherFilePath.isEmpty()) {
                throw new IllegalArgumentException("WEATHER_FILE_PATH environment variable is not set or empty.");
            }
            WeatherDataReader weatherDataReader = new CsvWeatherDataReader(
                    weatherFilePath);
            WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();

            List<WeatherData> weatherData = weatherDataReader.read();

            String dayWithSmallestTempSpread = String
                    .valueOf(weatherAnalyzer.getSmallestTempSpread(weatherData).getDay());
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
        } catch (WeatherDataReaderException e) {
            logger.error("An error occurred while reading weather data: {}", e.getMessage());
            System.err.printf("%s%n", e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.printf("%s%n", e.getMessage());
        }

        String countryFilePath = System.getenv("COUNTRY_FILE_PATH");
        try {
            if (countryFilePath == null || countryFilePath.isEmpty()) {
                throw new IllegalArgumentException("COUNTRY_FILE_PATH environment variable is not set or empty.");
            }
            CountryDataReader countryDataReader = new CsvCountryDataReader(countryFilePath);
            CountryAnalyzer countryAnalyzer = new CountryAnalyzer();
            List<CountryData> countryData = countryDataReader.read();
            String countryWithHighestPopulationDensity = countryAnalyzer.getMaxPopulationDensity(countryData).getName();
            System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
        } catch (CountryDataReaderException e) {
            logger.error("An error occurred while reading country data: {}", e.getMessage());
            System.err.printf("%s%n", e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.printf(e.getMessage());
        }
    }
}

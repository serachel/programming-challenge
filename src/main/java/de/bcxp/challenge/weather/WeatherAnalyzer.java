package de.bcxp.challenge.weather;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(WeatherAnalyzer.class);

    public WeatherData getSmallestTempSpread(List<WeatherData> weatherData) {
        logger.info("Finding smallest temperature spread");
        return weatherData.stream()
                .min(Comparator.comparingDouble(WeatherData::getTempSpread))
                .orElseThrow(() -> new IllegalArgumentException("No valid weather data found"));
    }
}
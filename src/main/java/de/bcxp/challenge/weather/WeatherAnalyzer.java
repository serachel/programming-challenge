package de.bcxp.challenge.weather;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(WeatherAnalyzer.class);

    public String getDayWithSmallestTempSpread(List<WeatherData> weatherData) {
        logger.info("Finding day with smallest temperature spread");
        return weatherData.stream()
                .min(Comparator.comparingDouble(WeatherData::getTempSpread))
                .map(data -> String.valueOf(data.getDay()))
                .orElseThrow(() -> new IllegalArgumentException("No valid weather data found"));
    }
}
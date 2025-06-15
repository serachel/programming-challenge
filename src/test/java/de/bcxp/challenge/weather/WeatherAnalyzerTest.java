package de.bcxp.challenge.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class WeatherAnalyzerTest {

    @Test
    void shouldGetRightTempSpread() {
        List<WeatherData> weatherDataList = List.of(
                new WeatherData(1, 20.0, 10.0), // spread = 10
                new WeatherData(2, 18.0, 15.0), // spread = 3
                new WeatherData(3, 25.0, 20.0) // spread = 5
        );
        WeatherAnalyzer analyzer = new WeatherAnalyzer();
        String result = analyzer.getDayWithSmallestTempSpread(weatherDataList);
        assertEquals("2", result);
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        List<WeatherData> weatherDataList = List.of(); // Empty list
        WeatherAnalyzer analyzer = new WeatherAnalyzer();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.getDayWithSmallestTempSpread(weatherDataList);
        });
        assertTrue(exception.getMessage().contains("No valid weather data found"));

    }

}

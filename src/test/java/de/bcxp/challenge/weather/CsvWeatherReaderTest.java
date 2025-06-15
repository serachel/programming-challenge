package de.bcxp.challenge.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class CsvWeatherReaderTest {

    @Test
    void shouldReadDataFromFileCorrectly() {
        WeatherDataReader reader = new CsvWeatherDataReader("src/test/resources/de/bcxp/challenge/weather.csv");
        List<WeatherData> data = reader.read();
        assertEquals(1, data.size());
        assertEquals(1, data.get(0).getDay());
        assertEquals(88, data.get(0).getMaxTemp());
        assertEquals(59, data.get(0).getMinTemp());
    }

    @Test
    void shouldThrowExceptionIfFilePathIsInvalid() {
        WeatherDataReader reader = new CsvWeatherDataReader("invalid/path.csv");
        WeatherDataReaderException exception = assertThrows(WeatherDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Error reading file:"));
    }

    @Test
    void shouldThrowExceptionIfRowIsIncomplete() {
        WeatherDataReader reader = new CsvWeatherDataReader(
                "src/test/resources/de/bcxp/challenge/weather_incomplete_row.csv");
        WeatherDataReaderException exception = assertThrows(WeatherDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Invalid CSV row: expected at least 3 columns. Row content"));
    }

    @Test
    void shouldThrowExceptionIfRowIsInvalid() {
        WeatherDataReader reader = new CsvWeatherDataReader(
                "src/test/resources/de/bcxp/challenge/weather_invalid_row.csv");
        WeatherDataReaderException exception = assertThrows(WeatherDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Invalid values in row: "));
    }
}

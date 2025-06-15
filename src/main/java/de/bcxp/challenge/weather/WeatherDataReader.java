package de.bcxp.challenge.weather;
import java.util.List;

public interface WeatherDataReader {
    List<WeatherData> read() throws WeatherDataReaderException;
}

package de.bcxp.challenge.weather;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvWeatherDataReader implements WeatherDataReader {
    private final String filePath;
    private static final Logger logger = LoggerFactory.getLogger(CsvWeatherDataReader.class);

    public CsvWeatherDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<WeatherData> read() throws WeatherDataReaderException {
        logger.info("Reading weather data from file: " + filePath);
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            return lines.stream()
                    .skip(1)
                    .map(line -> {
                        return parseWeatherData(line);
                    })
                    .collect(Collectors.toList());
        } catch (IOException | com.opencsv.exceptions.CsvException e) {
            throw new WeatherDataReaderException("Error reading file: " + e.getMessage());
        }
    }

    private WeatherData parseWeatherData(String[] line) {
        if (line.length < 3) {
            throw new WeatherDataReaderException(
                    "Invalid CSV row: expected at least 3 columns. Row content: " + String.join(",", line));
        }
        try {
            int day = Integer.parseInt(line[0].trim());
            double maxTemp = Double.parseDouble(line[1].trim());
            double minTemp = Double.parseDouble(line[2].trim());
            return new WeatherData(day, maxTemp, minTemp);
        } catch (NumberFormatException e) {
            throw new WeatherDataReaderException(
                    "Invalid values in row: " + String.join(",", line) + e.getMessage());
        }
    }
}
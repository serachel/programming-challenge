package de.bcxp.challenge.countries;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvCountryDataReader implements CountryDataReader {
    private final String filePath;
    private static final Logger logger = LoggerFactory.getLogger(CsvCountryDataReader.class);
    private final int columnIdxName = 0;
    private final int columnIdxArea = 4;
    private final int columnIdxPopulation = 3;

    public CsvCountryDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<CountryData> read() throws CountryDataReaderException {
        logger.info("Reading country data from file: " + filePath);
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new com.opencsv.CSVParserBuilder()
                        .withSeparator(';') // German CSVs often use semicolon as separator
                        .withQuoteChar('"') // Standard quote char
                        .build())
                .build()) {
            List<String[]> lines = reader.readAll();

            List<CountryData> data = lines.stream()
                    .skip(1) // skip header
                    .map(this::parseCountryData)
                    .collect(Collectors.toList());
            logger.info("Finished reading country data from file.");
            return data;
        } catch (IOException | com.opencsv.exceptions.CsvException e) {
            throw new CountryDataReaderException("Error reading file: " + e.getMessage());
        }
    }

    private CountryData parseCountryData(String[] line) {
        try {
            String name = line[columnIdxName].trim();
            int population = NumberFormat.getNumberInstance(Locale.GERMAN).parse(line[columnIdxPopulation].trim())
                    .intValue();
            int area = NumberFormat.getNumberInstance(Locale.GERMAN).parse(line[columnIdxArea].trim()).intValue();
            return new CountryData(name, population, area);
        } catch (NumberFormatException | ParseException e) {
            throw new CountryDataReaderException(
                    "Invalid values in row: " + String.join(";", line) + " " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {

            throw new CountryDataReaderException(
                    "Invalid CSV row: expected at least 5 columns. Row content: " + String.join(",", line));
        }
    }
}
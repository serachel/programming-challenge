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
    private int columnIdxName = 0;
    private int columnIdxArea = 0;
    private int columnIdxPopulation = 0;

    public CsvCountryDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<CountryData> read() throws CountryDataReaderException {
        logger.info("Reading country data from file: " + filePath);
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new com.opencsv.CSVParserBuilder()
                        .withSeparator(';')
                        .withQuoteChar('"')
                        .build())
                .build()) {
            List<String[]> lines = reader.readAll();
            this.columnIdxName = getColumnIndex(lines.get(0), "name");
            this.columnIdxPopulation = getColumnIndex(lines.get(0), "population");
            this.columnIdxArea = getColumnIndex(lines.get(0), "area");
            List<CountryData> data = lines.stream()
                    .skip(1)
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
            int population = parseGermanInt(line[columnIdxPopulation]);
            int area = parseGermanInt(line[columnIdxArea]);
            return new CountryData(name, population, area);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CountryDataReaderException(
                    "Invalid CSV row: expected at least 5 columns. Row content: " + String.join(",", line));
        }
    }

    private int getColumnIndex(String[] header, String columnName) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].trim().toLowerCase().contains(columnName)) {
                return i;
            }
        }
        throw new CountryDataReaderException("Column not found: " + columnName);
    }

    private int parseGermanInt(String value) {
        try {
            return NumberFormat.getNumberInstance(Locale.GERMAN).parse(value.trim()).intValue();
        } catch (NumberFormatException | ParseException e) {
            throw new CountryDataReaderException("Invalid number: " + value + e.getMessage());
        }
    }
}
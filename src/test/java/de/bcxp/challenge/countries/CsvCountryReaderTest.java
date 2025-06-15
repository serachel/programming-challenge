package de.bcxp.challenge.countries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CsvCountryReaderTest {
    @Test
    void shouldReadDataFromFileCorrectly() {
        CountryDataReader reader = new CsvCountryDataReader("src/test/resources/de/bcxp/challenge/countries.csv");
        List<CountryData> data = reader.read();
        assertEquals(2, data.size());

        assertEquals("Austria", data.get(0).getName());
        assertEquals(8926000, data.get(0).getPopulation());
        assertEquals(83855, data.get(0).getArea());

        assertEquals("Croatia", data.get(1).getName());
        assertEquals(4036355, data.get(1).getPopulation());
        assertEquals(56594, data.get(1).getArea());
    }

    @Test
    void shouldThrowExceptionIfFilePathIsInvalid() {
        CountryDataReader reader = new CsvCountryDataReader("invalid/path.csv");
        CountryDataReaderException exception = assertThrows(CountryDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Error reading file:"));
    }

    @Test
    void shouldThrowExceptionIfRowIsIncomplete() {
        CountryDataReader reader = new CsvCountryDataReader(
                "src/test/resources/de/bcxp/challenge/countries_incomplete_row.csv");
        CountryDataReaderException exception = assertThrows(CountryDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Invalid CSV row: expected at least 5 columns. Row content"));
    }

    @Test
    void shouldThrowExceptionIfRowIsInvalid() {
        CountryDataReader reader = new CsvCountryDataReader(
                "src/test/resources/de/bcxp/challenge/countries_invalid_row.csv");
        CountryDataReaderException exception = assertThrows(CountryDataReaderException.class, reader::read);
        assertTrue(exception.getMessage().contains("Invalid values in row:"));
    }
}

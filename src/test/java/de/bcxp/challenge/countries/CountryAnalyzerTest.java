package de.bcxp.challenge.countries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class CountryAnalyzerTest {

    @Test
    void shouldGetRightCountryWithHighestPopulationDensity() {
        List<CountryData> countryDataList = List.of(
                new CountryData("Austria", 8926000, 83855), // spread = 10
                new CountryData("Malta", 516100, 316), // spread = 3
                new CountryData("Belgium", 11566041, 30528) // spread = 5
        );
        CountryAnalyzer analyzer = new CountryAnalyzer();
        CountryData result = analyzer.getMaxPopulationDensity(countryDataList);
        assertEquals("Malta", result.getName());
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        List<CountryData> countryDataList = List.of(); // Empty list
        CountryAnalyzer analyzer = new CountryAnalyzer();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.getMaxPopulationDensity(countryDataList);
        });
        assertTrue(exception.getMessage().contains("No valid country data found"));

    }

}

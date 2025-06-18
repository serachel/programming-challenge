package de.bcxp.challenge.countries;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(CountryAnalyzer.class);

    public CountryData getMaxPopulationDensity(List<CountryData> countryData) {
        logger.info("Finding country with highest population density");
        return countryData.stream()
                .max(Comparator.comparingDouble(CountryData::getPopulationDensity))
                .orElseThrow(() -> new IllegalArgumentException("No valid country data found"));
    }
}
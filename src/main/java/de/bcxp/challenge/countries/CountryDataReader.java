package de.bcxp.challenge.countries;
import java.util.List;

public interface CountryDataReader {
    List<CountryData> read() throws CountryDataReaderException;
}

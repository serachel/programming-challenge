package de.bcxp.challenge.countries;

public class CountryData {
    private final String name;
    private final int population;
    private final int area;

    public CountryData(String name, int population, int area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public double getPopulationDensity() {
        return area == 0 ? 0 : (double) population / area;
    }
}
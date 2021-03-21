package countries;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

import java.time.ZoneId;

import static countries.Kiegeszitok.*;

public class Homework2 {

    private List<Country> countries;

    public Homework2() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns the longest country name translation.
     */
    public Optional<String> streamPipeline1() {
        return countries.stream().flatMap(country->country.getTranslations().values().stream()).max(Comparator.comparing(t->t.length()));
    }

    /**
     * Returns the longest Italian (i.e., {@code "it"}) country name translation.
     */
    public Optional<String> streamPipeline2() {
        return countries.stream().map(country->country.getTranslations().get("it")).filter(t->t!=null).max(Comparator.comparing(t->t.length()));
    }

    /**
     * Prints the longest country name translation together with its language code in the form language=translation.
     */
    public void streamPipeline3() {
        countries.stream().flatMap(country->country.getTranslations().entrySet().stream()).filter(trans->trans.toString().contains(countries.stream().flatMap(country->country.getTranslations().values().stream()).max(Comparator.comparing(t->t.length())).get())).forEach(System.out::println);
    }

    /**
     * Prints single word country names (i.e., country names that do not contain any space characters).
     */
    public void streamPipeline4() {
        countries.stream().map(Country::getName).filter(country->!country.contains(" ")).forEach(System.out::println);

    }

    /**
     * Returns the country name with the most number of words.
     */
    public Optional<String> streamPipeline5() {
        return countries.stream().map(Country::getName).max(Comparator.comparingInt(name->name.split(" ").length));
    }

    /**
     * Returns whether there exists at least one capital that is a palindrome.
     */
    public boolean streamPipeline6() {
        return countries.stream().map(Country::getCapital).filter(cap->cap != "").anyMatch(cap->cap.toLowerCase() ==  new StringBuilder(cap.toLowerCase()).reverse().toString());
    }

    /**
     * Returns the country name with the most number of {@code 'e'} characters ignoring case.
     */
    public Optional<String> streamPipeline7() {
        return countries.stream().map(country->country.getName()).max(Comparator.comparing(country->charCount(country.toLowerCase(),'e')));
    }

    /**
     *  Returns the capital with the most number of English vowels (i.e., {@code 'a'}, {@code 'e'}, {@code 'i'}, {@code 'o'}, {@code 'u'}).
     */
    public Optional<String> streamPipeline8() {
        return countries.stream().map(Country::getCapital).max(Comparator.comparing(cap->vowelCount(cap)));
    }

    /**
     * Returns a map that contains for each character the number of occurrences in country names ignoring case.
     */
    public Map<Character, Long> streamPipeline9() {
        return  countries.stream().flatMap(country->country.getName().chars().mapToObj(c -> (char) c)).collect(groupingBy(c->c,counting()));
    }

    /**
     * Returns a map that contains the number of countries for each possible timezone.
     */
    public Map<ZoneId, Long> streamPipeline10() {
        return countries.stream().flatMap(country->country.getTimezones().stream()).collect(groupingBy(t->t, counting()));
    }

    /**
     * Returns the number of country names by region that starts with their two-letter country code ignoring case.
     */
    public Map<Region, Long> streamPipeline11() {
        return countries.stream().filter(country->country.getName().substring(0,2).toUpperCase().equals(country.getCode().toUpperCase())).collect(groupingBy(country->country.getRegion(),counting()));
    }

    /**
     * Returns a map that contains the number of countries whose population is greater or equal than the population average versus the the number of number of countries with population below the average.
     */
    public Map<Boolean, Long> streamPipeline12() {
        return countries.stream().collect(groupingBy(country->country.getPopulation()>=countries.stream().mapToDouble(Country::getPopulation).average().getAsDouble(), counting()));
    }

    /**
     * Returns a map that contains for each country code the name of the corresponding country in Portuguese ({@code "pt"}).
     */
    public Map<String, String> streamPipeline13() {
        return countries.stream().collect(toMap(Country::getCode, country->country.getTranslations().get("pt")));
    }

    /**
     * Returns the list of capitals by region whose name is the same is the same as the name of their country.
     */
    public Map<Region, List<String>> streamPipeline14() {
        return countries.stream().collect(groupingBy(Country::getRegion, filtering(country->country.getName().equals(country.getCapital()),mapping(c->c.getCapital(),toList() ))));
    }

    /**
     *  Returns a map of country name-population density pairs.
     */
    public Map<String, Double> streamPipeline15() {
        return  countries.stream().collect(toMap(country->country.getName(), country->country.getArea() == null ? Double.NaN : country.getPopulation()/country.getArea().doubleValue()));
    }

}

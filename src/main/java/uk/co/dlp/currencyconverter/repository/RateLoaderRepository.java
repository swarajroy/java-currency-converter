package uk.co.dlp.currencyconverter.repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import uk.co.dlp.currencyconverter.domain.Exchange;
import uk.co.dlp.currencyconverter.domain.NoExchange;


public class RateLoaderRepository {

  private final Map<String, Exchange> rateExchangeMap;

  public RateLoaderRepository(final String fileName) {
    this.rateExchangeMap = getMap(fileName);
  }

  private Map<String, Exchange> getMap(String fileName) {
    if (fileName == null || Objects.equals("", fileName)) {
      return Map.of();
    }

    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

     return stream
          .filter(input -> !input.startsWith("COUNTRY") && !Objects.equals("", input))
          .map(input -> input.split(","))
          .map(input -> Arrays.stream(input).map(String::trim).toArray(String[]::new))
          .map(input -> new Exchange(input[0], input[1], input[2], new BigDecimal(input[3])))
          .collect(Collectors.toMap(Exchange::getCode, exchange -> exchange));

    } catch (IOException e) {
      return Map.of();
    }
  }

  public Exchange get(final String code) {
    return this.rateExchangeMap.getOrDefault(code, NoExchange.create());
  }

  public Collection<String> getCodes() {
    return this.rateExchangeMap.values().stream()
        .map(Exchange::getCode)
        .collect(Collectors.toList());
  }
}

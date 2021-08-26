package uk.co.dlp.currencyconverter.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ConvertedResult {
  private final BigDecimal amount;
  private final String country;
  private final String name;

  public ConvertedResult(BigDecimal amount, String country, String name) {
    this.amount = amount;
    this.country = country;
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount.abs();
  }

  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "ConvertedResult{" +
        "amount=" + amount +
        ", country='" + country + '\'' +
        ", name='" + name + '\'' +
        '}';
  }

  public boolean isPresent() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ConvertedResult that = (ConvertedResult) o;

    if (!Objects.equals(amount, that.amount)) {
      return false;
    }
    if (!Objects.equals(country, that.country)) {
      return false;
    }
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    int result = amount != null ? amount.hashCode() : 0;
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}

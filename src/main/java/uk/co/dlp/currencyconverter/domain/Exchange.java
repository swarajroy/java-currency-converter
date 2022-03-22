package uk.co.dlp.currencyconverter.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Exchange {
  private final String country;
  private final String name;
  private final String code;
  private final BigDecimal rate;

  public Exchange(String country, String name, String code, BigDecimal rate) {
    this.country = country;
    this.name = name;
    this.code = code;
    this.rate = rate;
  }


  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public BigDecimal getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return "Exchange{" +
        "country='" + country + '\'' +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", rate=" + rate +
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

    Exchange exchange = (Exchange) o;

    if (!Objects.equals(country, exchange.country)) {
      return false;
    }
    if (!Objects.equals(name, exchange.name)) {
      return false;
    }
    if (!Objects.equals(code, exchange.code)) {
      return false;
    }
    return Objects.equals(rate, exchange.rate);
  }

  @Override
  public int hashCode() {
    int result = country != null ? country.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (code != null ? code.hashCode() : 0);
    result = 31 * result + (rate != null ? rate.hashCode() : 0);
    return result;
  }
}

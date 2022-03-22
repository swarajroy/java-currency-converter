package uk.co.dlp.currencyconverter.domain;

import java.math.BigDecimal;

public class NoExchange extends Exchange {

  private static final NoExchange INSTANCE = new NoExchange();

  public NoExchange() {
    super("", "", "", BigDecimal.ZERO);
  }

  public static NoExchange create() {
    return INSTANCE;
  }

  @Override
  public boolean isPresent() {
    return false;
  }
}

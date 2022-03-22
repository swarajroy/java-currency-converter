package uk.co.dlp.currencyconverter.domain;

import java.math.BigDecimal;

public class NoConvertedResult extends ConvertedResult {

  private static final NoConvertedResult INSTANCE = new NoConvertedResult();


  public NoConvertedResult() {
    super(BigDecimal.ZERO, "", "");
  }

  public static NoConvertedResult create() {
    return INSTANCE;
  }

  @Override
  public boolean isPresent() {
    return false;
  }
}

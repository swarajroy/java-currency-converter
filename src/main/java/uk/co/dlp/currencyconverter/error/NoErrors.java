package uk.co.dlp.currencyconverter.error;

import java.util.Collections;

public class NoErrors extends Errors {

  private static final NoErrors INSTANCE = new NoErrors();

  public NoErrors() {
    super(Collections.emptyList());
  }

  public static NoErrors create() {
    return INSTANCE;
  }

  @Override
  public boolean isPresent() {
    return false;
  }
}

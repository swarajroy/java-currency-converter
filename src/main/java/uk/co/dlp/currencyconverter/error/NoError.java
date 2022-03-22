package uk.co.dlp.currencyconverter.error;

public class NoError extends Error {
  private static final NoError INSTANCE = new NoError();

  public NoError() {
    super("", "");
  }

  public static NoError create() {
    return INSTANCE;
  }

  @Override
  public boolean isPresent() {
    return false;
  }
}

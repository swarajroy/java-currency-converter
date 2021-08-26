package uk.co.dlp.currencyconverter.domain;

import java.util.Objects;
import uk.co.dlp.currencyconverter.error.Errors;

public class Outcome {

  private final ConvertedResult convertedResult;
  private final Errors errors;


  public Outcome(ConvertedResult convertedResult, Errors errors) {
    this.convertedResult = convertedResult;
    this.errors = errors;
  }

  public ConvertedResult getConvertedResult() {
    return convertedResult;
  }

  public Errors getErrors() {
    return errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Outcome outcome = (Outcome) o;

    if (!Objects.equals(convertedResult, outcome.convertedResult)) {
      return false;
    }
    return Objects.equals(errors, outcome.errors);
  }

  @Override
  public int hashCode() {
    int result = convertedResult != null ? convertedResult.hashCode() : 0;
    result = 31 * result + (errors != null ? errors.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Outcome{" +
        "convertedResult=" + convertedResult +
        ", errors=" + errors +
        '}';
  }
}

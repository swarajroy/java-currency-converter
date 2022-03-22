package uk.co.dlp.currencyconverter.error;

import java.util.Collection;
import java.util.Objects;

public class Errors {
  private final Collection<Error> errors;

  public Errors(Collection<Error> errors) {
    this.errors = errors;
  }

  public boolean isPresent() {
    return !Objects.isNull(errors) &&!errors.isEmpty();
  }

  public Collection<Error> getErrors() {
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

    Errors errors1 = (Errors) o;

    return Objects.equals(errors, errors1.errors);
  }

  @Override
  public int hashCode() {
    return errors != null ? errors.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Errors{" +
        "errors=" + errors +
        '}';
  }
}

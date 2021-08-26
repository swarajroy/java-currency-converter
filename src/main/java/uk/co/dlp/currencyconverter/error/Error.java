package uk.co.dlp.currencyconverter.error;

import java.util.Objects;

public class Error {
  private final String key;
  private final String message;


  public Error(String key, String message) {
    this.key = key;
    this.message = message;
  }


  public String getKey() {
    return key;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "Error{" +
        "key='" + key + '\'' +
        ", message='" + message + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Error error = (Error) o;

    if (!Objects.equals(key, error.key)) {
      return false;
    }
    return Objects.equals(message, error.message);
  }

  @Override
  public int hashCode() {
    int result = key != null ? key.hashCode() : 0;
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }

  public boolean isPresent() {
    return true;
  }
}

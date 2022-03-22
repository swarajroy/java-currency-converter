package uk.co.dlp.currencyconverter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import uk.co.dlp.currencyconverter.domain.Exchange;
import uk.co.dlp.currencyconverter.domain.NoExchange;

class RateLoaderRepositoryTest {

  public static final String TEST_FILE_NAME = "src/test/resources/test-currency-rates-base-gbp.txt";


  @Test
  public void shouldExpectNoExchangesWhenFileNameIsNull() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository(null);
    assertThat(subjectUnderTest.get("doesntMatter")).isEqualTo(NoExchange.create());
  }

  @Test
  public void shouldExpectNoExchangesWhenFileNameIsEmpty() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository("");
    assertThat(subjectUnderTest.get("doesntMatter")).isEqualTo(NoExchange.create());
  }

  @Test
  public void shouldExpectNoExchangesWhenFileNameIsInvalid() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository("wrongFileName");
    assertThat(subjectUnderTest.get("doesntMatter")).isEqualTo(NoExchange.create());
  }

  @Test
  public void shouldExpectNoExchangeWhenFileNameIsCorrectAndSuppliedIncorrectKey() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository(TEST_FILE_NAME);
    assertThat(subjectUnderTest.get("NEXX")).isEqualTo(NoExchange.create());
  }

  @Test
  public void shouldExpectExchangeWhenFileNameIsCorrectAndSuppliedCorrectKey() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository(TEST_FILE_NAME);
    assertThat(subjectUnderTest.get("AED")).isEqualTo(new Exchange("United Arab Emirates", "Dirhams", "AED", new BigDecimal("5.04")));
  }

  @Test
  public void shouldGetCodes() {
    RateLoaderRepository subjectUnderTest = new RateLoaderRepository(TEST_FILE_NAME);
    assertThat(subjectUnderTest.getCodes()).isNotEmpty();
    assertThat(subjectUnderTest.getCodes()).containsExactlyInAnyOrder("AED", "GBP", "AUD", "USD");
  }


}
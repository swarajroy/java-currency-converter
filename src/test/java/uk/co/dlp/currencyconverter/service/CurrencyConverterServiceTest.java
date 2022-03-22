package uk.co.dlp.currencyconverter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.dlp.currencyconverter.domain.ConvertedResult;
import uk.co.dlp.currencyconverter.domain.Exchange;
import uk.co.dlp.currencyconverter.domain.NoConvertedResult;
import uk.co.dlp.currencyconverter.domain.Outcome;
import uk.co.dlp.currencyconverter.error.Error;
import uk.co.dlp.currencyconverter.error.Errors;
import uk.co.dlp.currencyconverter.error.NoErrors;
import uk.co.dlp.currencyconverter.repository.RateLoaderRepository;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

  private CurrencyConverterService subjectUnderTest;

  @Mock
  private RateLoaderRepository rateLoaderRepository;

  @BeforeEach
  public void setUp() {
    this.subjectUnderTest = new CurrencyConverterService(rateLoaderRepository);
  }

  static Stream<Arguments> invalidCurrencyCodes() {
    return Stream.of(
        Arguments.of(anyAmount(), invalidCurrencyCode(), invalidCurrencyCode(), expectedOutcome(NoConvertedResult.create(), new Errors(List.of(
            new Error("source code", "ISO 4217 code invalid"),
            new Error("target code", "ISO 4217 code invalid")
        )))),
        Arguments.of(anyAmount(), validSourceCurrencyCode(), invalidCurrencyCode(), expectedOutcome(NoConvertedResult.create(), new Errors(List.of(
            new Error("target code", "ISO 4217 code invalid")
        )))),
        Arguments.of(anyAmount(), invalidCurrencyCode(), validTargetCurrencyCode(), expectedOutcome(NoConvertedResult.create(), new Errors(List.of(
            new Error("source code", "ISO 4217 code invalid")
        ))))
    );
  }

  static Stream<Arguments> testArgs() {
    return Stream.of(
        // GBP to AED
        Arguments.of(
            validExchange("United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling", "GBP", new BigDecimal("1.00")), validExchange("United Arab Emirates", "Dirhams", "AED", new BigDecimal("5.04")), anyAmount(), validSourceCurrencyCode(), validTargetCurrencyCode(), expectedOutcome(new ConvertedResult(new BigDecimal("34.13"), "United Arab Emirates", "Dirhams"), NoErrors.create())
        ),
        // AED to GBP
        Arguments.of(
            validExchange("United Arab Emirates", "Dirhams", "AED", new BigDecimal("5.04")), validExchange("United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling", "GBP", new BigDecimal("1.00")), anyAmount(), validSourceCurrencyCode(), validTargetCurrencyCode(), expectedOutcome(new ConvertedResult(new BigDecimal("1.36"), "United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling"), NoErrors.create())
        ),
        // GBP to GBP
        Arguments.of(
            validExchange("United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling", "GBP", new BigDecimal("1.00")), validExchange("United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling", "GBP", new BigDecimal("1.00")), anyAmount(), validSourceCurrencyCode(), validTargetCurrencyCode(), expectedOutcome(new ConvertedResult(new BigDecimal("6.77"), "United Kingdom Of Great Britain and Northern Ireland", "Pound Sterling"), NoErrors.create())
        ),
        // USD to AUD
        Arguments.of(
            validExchange("United States Of America", "US Dollar", "USD", new BigDecimal("1.37")), validExchange("Australia", "Dollars", "AUD", new BigDecimal("1.8914137")), anyAmount(), validSourceCurrencyCode(), validTargetCurrencyCode(), expectedOutcome(new ConvertedResult(new BigDecimal("9.35"), "Australia", "Dollars"), NoErrors.create())
        )
    );
  }



  @ParameterizedTest
  @MethodSource("invalidCurrencyCodes")
  public void testInvalidInput(final BigDecimal amount, final String sourceCurrCode, final String targetCurrCode, final Outcome result) {
    when(rateLoaderRepository.getCodes()).thenReturn(List.of("GBP", "AED"));

    assertThat(this.subjectUnderTest.convert(amount, sourceCurrCode, targetCurrCode)).isEqualTo(result);

    verify(rateLoaderRepository, times(2)).getCodes();
    verifyNoMoreInteractions(rateLoaderRepository);
  }

  @ParameterizedTest
  @MethodSource("testArgs")
  public void test(final Exchange src, final Exchange dest, final BigDecimal amount, final String sourceCurrCode, final String targetCurrCode, final Outcome expected) {
    when(rateLoaderRepository.getCodes()).thenReturn(List.of("GBP", "AED"));
    when(rateLoaderRepository.get(anyString())).thenReturn(src, dest);

    assertThat(this.subjectUnderTest.convert(amount, sourceCurrCode, targetCurrCode)).isEqualTo(expected);

    verify(rateLoaderRepository, times(2)).getCodes();
    verify(rateLoaderRepository, times(2)).get(anyString());
  }

  private static Exchange validExchange(String country, String name, String code, BigDecimal rate) {
    return new Exchange(country, name, code, rate);
  }

  private static String invalidCurrencyCode() {
    return "JKX";
  }

  private static Outcome expectedOutcome(final ConvertedResult convertedResult, final Errors errors) {
    return new Outcome(convertedResult, errors);
  }

  private static String validTargetCurrencyCode() {
    return "AED";
  }

  private static String validSourceCurrencyCode() {
    return "GBP";
  }

  private static BigDecimal anyAmount() {
    return new BigDecimal( "6.77");
  }
}
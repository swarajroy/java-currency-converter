package uk.co.dlp.currencyconverter.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import uk.co.dlp.currencyconverter.domain.ConvertedResult;
import uk.co.dlp.currencyconverter.domain.Exchange;
import uk.co.dlp.currencyconverter.domain.NoConvertedResult;
import uk.co.dlp.currencyconverter.domain.Outcome;
import uk.co.dlp.currencyconverter.error.Error;
import uk.co.dlp.currencyconverter.error.Errors;
import uk.co.dlp.currencyconverter.error.NoError;
import uk.co.dlp.currencyconverter.error.NoErrors;
import uk.co.dlp.currencyconverter.repository.RateLoaderRepository;

public class CurrencyConverterService {

  private final RateLoaderRepository rateLoaderRepository;

  public CurrencyConverterService(final RateLoaderRepository rateLoaderRepository) {
    this.rateLoaderRepository = rateLoaderRepository;
  }

  public Outcome convert(final BigDecimal amount, final String sourceCurrencyCode, final String targetCurrencyCode) {

    final Errors errors = validateInput(sourceCurrencyCode, targetCurrencyCode);

    if (errors.isPresent()) {
      return new Outcome(NoConvertedResult.create(), errors);
    }

    final Exchange src = this.rateLoaderRepository.get(sourceCurrencyCode);
    final Exchange dest = this.rateLoaderRepository.get(targetCurrencyCode);
    final BigDecimal temp = dest.getRate().divide(src.getRate(), RoundingMode.HALF_EVEN);
    final BigDecimal result = amount.multiply(temp).setScale(2, RoundingMode.UP);
    return new Outcome(new ConvertedResult(result, dest.getCountry(), dest.getName()), NoErrors.create());
  }

  private Errors validateInput(final String sourceCurrencyCode, final String targetCurrencyCode) {
    final Collection<Error> errors = new ArrayList<>();

    final Error sourceCurrencyCodeError = validateCode(sourceCurrencyCode, "source");
    if (sourceCurrencyCodeError.isPresent()) {
      errors.add(sourceCurrencyCodeError);
    }

    final Error targetCurrencyCodeError = validateCode(targetCurrencyCode, "target");
    if (targetCurrencyCodeError.isPresent()) {
      errors.add(targetCurrencyCodeError);
    }

    if (errors.isEmpty()) {
      return NoErrors.create();
    }

    return new Errors(errors);
  }

  private Error validateCode(final String code, final String direction) {
    final Collection<String> validCodes = rateLoaderRepository.getCodes();
    if (!validCodes.contains(code)) {
      return new Error(direction.concat(" ").concat("code"), "ISO 4217 code invalid");
    }
    return NoError.create();
  }

}

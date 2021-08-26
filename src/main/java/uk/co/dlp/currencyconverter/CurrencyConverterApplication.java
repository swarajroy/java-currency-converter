package uk.co.dlp.currencyconverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import uk.co.dlp.currencyconverter.domain.Outcome;
import uk.co.dlp.currencyconverter.repository.RateLoaderRepository;
import uk.co.dlp.currencyconverter.service.CurrencyConverterService;

public class CurrencyConverterApplication {

  private static final String FILENAME = "src/main/resources/currency-rates-base-gbp.txt";
  private static final DecimalFormat INPUT_AMOUNT_FORMATTER = new DecimalFormat("#.##");

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter amount (#.##): ");
      final BigDecimal amount = new BigDecimal(
          INPUT_AMOUNT_FORMATTER.format(scanner.nextBigDecimal().abs()));

      System.out.println("Enter source currency code (ISO 4217): ");
      final String sourceCurrencyCode = scanner.next().toUpperCase();

      System.out.println("Enter target currency code (ISO 4217): ");
      final String targetCurrencyCode = scanner.next().toUpperCase();

      CurrencyConverterService service = new CurrencyConverterService(new RateLoaderRepository(FILENAME));

      final Outcome outcome = service.convert(amount, sourceCurrencyCode, targetCurrencyCode);

      if (outcome.getConvertedResult().isPresent()) {
        System.out.println("Amount = "+ outcome.getConvertedResult().getAmount());
        System.out.println("Country = "+ outcome.getConvertedResult().getCountry());
        System.out.println("Name = "+ outcome.getConvertedResult().getName());
      } else if (outcome.getErrors().isPresent()) {
        outcome.getErrors().getErrors()
            .forEach(System.out::println);
      }
    } catch (final IllegalArgumentException  | InputMismatchException ex) {
      System.out.println("amount: Invalid format. Accepted format is decimal number upto 2 decimal places");
    }
  }

}

package gohil.jay.NumberScanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class NumberScanner {
    private static final String ILLEGAL_SIGNATURE_RESPONSE = "ILL";

    private final DigitLookupService digitLookupService;
    private final FileParser fileParser;

    NumberScanner(@Autowired DigitLookupService digitLookupService, @Autowired FileParser fileParser) {
        this.digitLookupService = digitLookupService;
        this.fileParser = fileParser;
    }

    List<String> scan(final File file) {
        try (final InputStream inputStream = new FileInputStream(file)) {
            final List<EncodedNumber> encodedNumbers = fileParser.parseNumbers(inputStream);
            return encodedNumbers.stream().map(this::convert).collect(Collectors.toList());
        } catch (IOException e) {
            throw new NumberScannerException(String.format("unable to scan file %s", file));
        }
    }

        private String convert(final EncodedNumber encodedNumber) {
        final StringBuilder numberString = new StringBuilder();
        IntStream.rangeClosed(1, EncodedNumber.NUM_DIGITS_IN_NUMBER).forEach(i->{
            final String digitSignature = encodedNumber.getDigitalSignature(i);
            final Optional<Integer> digit = digitLookupService.retrieveDigit(digitSignature);
            digit.ifPresent(numberString::append);
        });

        if (numberString.length()<EncodedNumber.NUM_DIGITS_IN_NUMBER) {
            return ILLEGAL_SIGNATURE_RESPONSE;
        }
        else {
            return numberString.toString();
        }
    }
}

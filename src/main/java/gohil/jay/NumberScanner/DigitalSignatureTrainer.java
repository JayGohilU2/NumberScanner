package gohil.jay.NumberScanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/** Computes the digital signatures for each digit in the training file. Also has logic to assign a pre-designated
 *  signature for the digit 0 (as it does not appear in the training file).
 */
@Component
class DigitalSignatureTrainer {
    private static Logger log = LoggerFactory.getLogger(DigitalSignatureTrainer.class);
    private final FileParser fileParser;
    private final Resource trainingResource;

    private final String digit0Signature = " _ | ||_|";

    DigitalSignatureTrainer(@Autowired final FileParser fileParser,
                                   @Value("classpath:training.txt") final Resource trainingResource) {
        this.fileParser = fileParser;
        this.trainingResource = trainingResource;
    }

    Map<String,Integer> train()  {
        final List<EncodedNumber> encodedNumbers;
        try(final InputStream inputStream=trainingResource.getInputStream()) {
            encodedNumbers = fileParser.parseNumbers(inputStream);
        }
        catch (IOException e) {
            throw new NumberScannerException(String.format("unable to load training file %s", trainingResource));
        }

        if (encodedNumbers.size() != 1) {
            throw new NumberScannerException(String.format("training file %s should resolve to a single number",
                    trainingResource));
        }

        final Map<String,Integer> digitLookupMap = new HashMap<>();
        final EncodedNumber encodedNumber = encodedNumbers.get(0);
        IntStream.rangeClosed(1,EncodedNumber.NUM_DIGITS_IN_NUMBER).forEach(digit-> {
                    final String digitSignature = encodedNumber.getDigitalSignature(digit);
                    digitLookupMap.put(digitSignature, digit);
                });
        // During training, the digit 0's signature is not captured. So based on the image representation of 0 in the
        // file we can deduce the signature...
        digitLookupMap.put(digit0Signature, 0);
        return digitLookupMap;
    }
}

package gohil.jay.NumberScanner;

import java.util.List;

/** Represents an ordered sequence of digits characterised by their digital sequence as represented in the input file */
class EncodedNumber {
    static final int NUM_DIGITS_IN_NUMBER = 9;

    private final List<String> digitalSequences;

    EncodedNumber(final List<String> digitalSequences) {
        this.digitalSequences = digitalSequences;
    }

    String getDigitalSignature(final int digitIndex) {
        if (digitIndex < 1 || digitIndex >digitalSequences.size()) {
            throw new RuntimeException(String.format("unable to retrieve digitial signature for digitIndex=%d",
                    digitIndex));
        }
        return digitalSequences.get(digitIndex-1);
    }
}

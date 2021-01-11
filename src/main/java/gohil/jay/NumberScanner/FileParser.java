package gohil.jay.NumberScanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class FileParser {
    static final String CORRUPT_DATA_REPLACEMENT = "?";

    private static final int MAX_CHARS_IN_LINE = 27;
    private static final int NUM_LINES_PER_NUMBER = 3;
    private static final int NUM_CHARS_PER_NUMBER_LINE = 3;


    private static final String CORRUPT_DATA_DETECTOR = "[^|_ ]";

    List<EncodedNumber> parseNumbers(final InputStream inputStream) {
        final List<String> lines = slurpFile(inputStream);
        // Remove corrupt characters from file and replace with well-known "bad" value....
        final List<String> cleanedLines = lines.stream()
                .map(line->line.replaceAll(CORRUPT_DATA_DETECTOR, CORRUPT_DATA_REPLACEMENT))
                .collect(Collectors.toList());

        final List<EncodedNumber> encodedNumbers = new ArrayList<>();

        final List<String> linesForNumber = new ArrayList(NUM_LINES_PER_NUMBER);
        cleanedLines.forEach(line->{
            if (!line.isBlank()) {
                linesForNumber.add(line);
                if (linesForNumber.size()==NUM_LINES_PER_NUMBER) {
                    final EncodedNumber encodedNumber = parseLines(linesForNumber);
                    encodedNumbers.add(encodedNumber);
                    linesForNumber.clear();
                }
            }
        });
        return encodedNumbers;
    }

    private EncodedNumber parseLines(final List<String> linesForNumber) {
        final List<StringBuilder> holdingData = IntStream.rangeClosed(1, EncodedNumber.NUM_DIGITS_IN_NUMBER)
                .mapToObj(StringBuilder::new)
                .collect(Collectors.toList());

        IntStream.range(0, NUM_LINES_PER_NUMBER).forEach(lineNum-> {
            final String line = linesForNumber.get(lineNum);
            if (line.length() != MAX_CHARS_IN_LINE) {
                throw new NumberScannerException(String.format("invalid line length for line=%s", line));
            }

            IntStream.range(0, EncodedNumber.NUM_DIGITS_IN_NUMBER).forEach(idx-> {
                int startPos = idx*NUM_CHARS_PER_NUMBER_LINE;
                final String digitLine = line.substring(startPos, startPos+NUM_CHARS_PER_NUMBER_LINE);
                holdingData.set(idx, holdingData.get(idx).append(digitLine));
            });
        });

        final List<String> encodedStrings = holdingData.stream()
                .map(StringBuilder::toString)
                .collect(Collectors.toList());

        return new EncodedNumber(encodedStrings);
    }


    private List<String> slurpFile(final InputStream inputStream) {
        final List<String> lines = new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line=reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new NumberScannerException(String.format("unable to slurp file from %s", inputStream));
        }
        return lines;
    }
}

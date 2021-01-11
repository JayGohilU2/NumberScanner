package gohil.jay.NumberScanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= FileParser.class)

class FileParserTest {
    @SuppressWarnings("unused")
    @Autowired
    private FileParser unit;

    @Test
    public void WHEN_training_file_is_read_THAT_digit_data_is_processed_correctly() throws IOException {
        final File file = new ClassPathResource("training.txt").getFile();
        final List<EncodedNumber> encodedNumbers = unit.parseNumbers(new FileInputStream(file));

        assertThat(encodedNumbers, hasSize(1));
        final EncodedNumber encodedNumber = encodedNumbers.get(0);

        assertThat(encodedNumber.getDigitalSignature(1), is("     |  |"));
        assertThat(encodedNumber.getDigitalSignature(2), is(" _  _||_ "));
        assertThat(encodedNumber.getDigitalSignature(3), is(" _  _| _|"));
        assertThat(encodedNumber.getDigitalSignature(4), is("   |_|  |"));
        assertThat(encodedNumber.getDigitalSignature(5), is(" _ |_  _|"));
        assertThat(encodedNumber.getDigitalSignature(6), is(" _ |_ |_|"));
        assertThat(encodedNumber.getDigitalSignature(7), is(" _   |  |"));
        assertThat(encodedNumber.getDigitalSignature(8), is(" _ |_||_|"));
        assertThat(encodedNumber.getDigitalSignature(9), is(" _ |_| _|"));
    }


    @Test
    public void WHEN_file_contains_illegal_characters_THAT_QUESTION_MARK_IS_SUBSTITUTED() throws IOException {
        final File file = new ClassPathResource("bad_numbers_file.txt").getFile();
        final List<EncodedNumber> encodedNumbers = unit.parseNumbers(new FileInputStream(file));

        assertThat(encodedNumbers, hasSize(1));
        final EncodedNumber encodedNumber = encodedNumbers.get(0);

        assertThat(encodedNumber.getDigitalSignature(1), is("  ?  |  |"));
        assertThat(encodedNumber.getDigitalSignature(2), is(" _  _||_ "));
        assertThat(encodedNumber.getDigitalSignature(3), is(" _  _| ?|"));
        assertThat(encodedNumber.getDigitalSignature(4), is("   |_|  |"));
        assertThat(encodedNumber.getDigitalSignature(5), is(" ? |_  _|"));
        assertThat(encodedNumber.getDigitalSignature(6), is(" _ |? |_|"));
        assertThat(encodedNumber.getDigitalSignature(7), is(" _   |  |"));
        assertThat(encodedNumber.getDigitalSignature(8), is(" _ |_||_|"));
        assertThat(encodedNumber.getDigitalSignature(9), is(" _ |_| _?"));
    }

}
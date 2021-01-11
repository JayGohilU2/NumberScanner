package gohil.jay.NumberScanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={NumberScanner.class, DigitLookupService.class, FileParser.class,
DigitalSignatureTrainer.class})
class NumberScannerTest {
    @SuppressWarnings("unused")
    @Autowired
    private NumberScanner unit;

    @Test
    public void GIVEN_training_file_THAT_processed_successfully() throws IOException {
        final File file = new ClassPathResource("training.txt").getFile();
        final List<String> numbers = unit.scan(file);
        assertThat(numbers, hasSize(1));
        assertThat(numbers.get(0), is("123456789"));
    }


    @Test
    public void GIVEN_multipleChunks_file_THAT_processed_successfully() throws IOException {
        final File file = new ClassPathResource("multipleChunks").getFile();
        final List<String> numbers = unit.scan(file);
        assertThat(numbers, hasSize(3));
        assertThat(numbers.get(0), is("123456789"));
        assertThat(numbers.get(1), is("123456789"));
        assertThat(numbers.get(2), is("123456789"));
    }

    @Test
    public void GIVEN_multipleChunksWithIllegalRow_file_THAT_processed_successfully() throws IOException {
        final File file = new ClassPathResource("multipleChunksWithIllegalRow").getFile();
        final List<String> numbers = unit.scan(file);
        assertThat(numbers, hasSize(3));
        assertThat(numbers.get(0), is("123456789"));
        assertThat(numbers.get(1), is("123456?89 ILL"));
        assertThat(numbers.get(2), is("123456789"));
    }


    @Test
    public void GIVEN_singleChunk_file_THAT_processed_successfully() throws IOException {
        final File file = new ClassPathResource("singleChunk").getFile();
        final List<String> numbers = unit.scan(file);
        assertThat(numbers, hasSize(1));
        assertThat(numbers.get(0), is("000000000"));
    }

    @Test
    public void GIVEN_another_bad_file_THAT_processed_unsuccessfully() throws IOException {
        final File file = new ClassPathResource("bad_numbers_file.txt").getFile();
        final List<String> numbers = unit.scan(file);
        assertThat(numbers, hasSize(1));
        assertThat(numbers.get(0), is("?2?4??78? ILL"));
    }

}
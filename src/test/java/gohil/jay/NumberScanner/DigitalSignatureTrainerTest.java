package gohil.jay.NumberScanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={DigitalSignatureTrainer.class, FileParser.class})
class DigitalSignatureTrainerTest {

    @SuppressWarnings("unused")
    @Autowired
    private DigitalSignatureTrainer unit;

    @Test
    public void WHEN_training_is_complete_THAT_all_digitail_signatures_are_available() {
        final Map<String, Integer> trainedSequences = unit.train();
        assertThat(trainedSequences.entrySet(), hasSize(10));
        assertThat(trainedSequences.get(" _ | ||_|"), is(0));
        assertThat(trainedSequences.get("     |  |"), is(1));
        assertThat(trainedSequences.get(" _  _||_ "), is(2));
        assertThat(trainedSequences.get(" _  _| _|"), is(3));
        assertThat(trainedSequences.get("   |_|  |"), is(4));
        assertThat(trainedSequences.get(" _ |_  _|"), is(5));
        assertThat(trainedSequences.get(" _ |_ |_|"), is(6));
        assertThat(trainedSequences.get(" _   |  |"), is(7));
        assertThat(trainedSequences.get(" _ |_||_|"), is(8));
        assertThat(trainedSequences.get(" _ |_| _|"), is(9));
    }
}
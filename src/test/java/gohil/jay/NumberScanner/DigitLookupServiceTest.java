package gohil.jay.NumberScanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={DigitLookupService.class, DigitalSignatureTrainer.class, FileParser.class})
class DigitLookupServiceTest {

    @SuppressWarnings("unused")
    @Autowired
    private DigitLookupService unit;


    @Test
    public void WHEN_digital_signature_for_1_THEN_1() {
        final Optional<Integer> oneDigit = unit.retrieveDigit("     |  |");
        assertTrue(oneDigit.isPresent());
        assertThat(oneDigit.get(), is(1));
    }

    @Test
    public void WHEN_digital_signature_for_2_THEN_2() {
        final Optional<Integer> twoDigit = unit.retrieveDigit(" _  _||_ ");
        assertTrue(twoDigit.isPresent());
        assertThat(twoDigit.get(), is(2));
    }


    @Test
    public void WHEN_digital_signature_for_3_THEN_3() {
        final Optional<Integer> threeDigit = unit.retrieveDigit(" _  _| _|");
        assertTrue(threeDigit.isPresent());
        assertThat(threeDigit.get(), is(3));
    }

    @Test
    public void WHEN_digital_signature_for_4_THEN_4() {
        final Optional<Integer> fourDigit = unit.retrieveDigit("   |_|  |");
        assertTrue(fourDigit.isPresent());
        assertThat(fourDigit.get(), is(4));
    }

    @Test
    public void WHEN_digital_signature_for_5_THEN_5() {
        final Optional<Integer> fiveDigit = unit.retrieveDigit(" _ |_  _|");
        assertTrue(fiveDigit.isPresent());
        assertThat(fiveDigit.get(), is(5));
    }

    @Test
    public void WHEN_digital_signature_for_6_THEN_6() {
        final Optional<Integer> sixDigit = unit.retrieveDigit(" _ |_ |_|");
        assertTrue(sixDigit.isPresent());
        assertThat(sixDigit.get(), is(6));
    }

    @Test
    public void WHEN_digital_signature_for_7_THEN_7() {
        final Optional<Integer> sevenDigit = unit.retrieveDigit(" _   |  |");
        assertTrue(sevenDigit.isPresent());
        assertThat(sevenDigit.get(), is(7));
    }


    @Test
    public void WHEN_digital_signature_for_8_THEN_8() {
        final Optional<Integer> eightDigit = unit.retrieveDigit(" _ |_||_|");
        assertTrue(eightDigit.isPresent());
        assertThat(eightDigit.get(), is(8));
    }

    @Test
    public void WHEN_digital_signature_for_9_THEN_9() {
        final Optional<Integer> nineDigit = unit.retrieveDigit(" _ |_| _|");
        assertTrue(nineDigit.isPresent());
        assertThat(nineDigit.get(), is(9));
    }

    @Test
    public void WHEN_digital_signature_for_0_THEN_0() {
        final Optional<Integer> zeroDigit = unit.retrieveDigit(" _ | ||_|");
        assertTrue(zeroDigit.isPresent());
        assertThat(zeroDigit.get(), is(0));
    }


    @Test
    public void WHEN_corrupt_data_is_read_THEN_empty_is_returned() {
        final Optional<Integer> corrupt = unit.retrieveDigit("---? |___");
        assertTrue(corrupt.isEmpty());
    }


}
package gohil.jay.NumberScanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
class DigitLookupService {
    private static final Logger log = LoggerFactory.getLogger(DigitLookupService.class);

    private @Autowired final DigitalSignatureTrainer digitalSignatureTrainer;
    private final Map<String,Integer> digitalSignatureMap = new HashMap<>();

    DigitLookupService(@Autowired DigitalSignatureTrainer digitalSignatureTrainer) {
        this.digitalSignatureTrainer = digitalSignatureTrainer;
    }


    @SuppressWarnings("unused")
    @PostConstruct
    void initialise() {
        digitalSignatureMap.putAll(digitalSignatureTrainer.train());
        log.info("digital signatures have been trained: {}", digitalSignatureMap);
    }

    Optional<Integer> retrieveDigit(final String digitalSignature) {
        return Optional.ofNullable(digitalSignatureMap.get(digitalSignature));
    }
}

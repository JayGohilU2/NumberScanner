package gohil.jay.NumberScanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class NumberScannerApplication implements CommandLineRunner {

	@SuppressWarnings("unused")
	private @Autowired ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(NumberScannerApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		if (args.length !=1) {
			throw new NumberScannerException(String.format("usage: %s <input-file-name>",
					NumberScannerApplication.class.getSimpleName()));
		}

		final NumberScanner numberScanner = applicationContext.getBean(NumberScanner.class);
		final List<String> numbers = numberScanner.scan(Paths.get(args[0]).toFile());

		numbers.forEach(number->{
			System.out.println(number);
		});
	}
}

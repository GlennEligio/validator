package com.glenneligio.dataloadvalidator;

import com.glenneligio.dataloadvalidator.commands.DataloadValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.*;


@SpringBootApplication
public class DataloadValidatorApplication implements CommandLineRunner, ExitCodeGenerator {

	private IFactory iFactory;
	private DataloadValidator dataloadValidator;
	private int exitCode;

	public DataloadValidatorApplication(IFactory iFactory, DataloadValidator validator) {
		this.iFactory = iFactory;
		this.dataloadValidator = validator;
	}


	public static void main(String[] args) {
		// let Spring instantiate and inject dependencies
		SpringApplication.run(DataloadValidatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		exitCode = new CommandLine(dataloadValidator, iFactory).execute(args);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}
}

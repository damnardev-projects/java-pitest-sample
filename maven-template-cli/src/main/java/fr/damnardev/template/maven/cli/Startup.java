package fr.damnardev.template.maven.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.damnardev.template.maven.common.Calculator;

public class Startup {

	private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

	public static void main(String[] args) {
		LOGGER.info("10 + 10 = {}", Calculator.add(10, 10));
	}

}

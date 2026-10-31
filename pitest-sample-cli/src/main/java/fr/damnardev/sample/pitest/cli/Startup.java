package fr.damnardev.sample.pitest.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.damnardev.sample.pitest.common.Calculator;

public class Startup {

	private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

	public static void main(String[] args) {
		LOGGER.info("10 + 10 = {}", Calculator.add(10, 10));
	}

}

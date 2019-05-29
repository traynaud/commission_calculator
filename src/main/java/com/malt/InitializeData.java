package com.malt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initial load of configuration files
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Component
public class InitializeData implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(InitializeData.class);

	@Override
	public void run(final String... args) throws Exception {
		// TODO
	}
}

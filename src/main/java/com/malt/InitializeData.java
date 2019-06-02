package com.malt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Rule;
import com.malt.services.RulesService;

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

	@Autowired
	private RulesService rulesService;

	@Transactional
	@Override
	public void run(final String... args) throws Exception {

		final long t1 = System.currentTimeMillis();
		final List<Rule> rules = rulesService.parseDirectory();
		logger.info("Initialisation completed: {} rules have been parsed in {} ms", rules.size(),
				System.currentTimeMillis() - t1);
	}
}

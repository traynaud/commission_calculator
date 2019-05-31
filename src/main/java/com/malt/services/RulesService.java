package com.malt.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This {@link Service} manage the elements related to the rules
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Service
public class RulesService {

	private static final Logger logger = LoggerFactory.getLogger(RulesService.class);

	@Value("${rules_directory}")
	private String rulesDirectory;

	// Check only rules that have a fee stricly inferior to the better found
}

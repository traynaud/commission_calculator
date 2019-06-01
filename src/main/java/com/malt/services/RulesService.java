package com.malt.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Rule;
import com.malt.repositories.RuleRepository;

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

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private ConditionService conditionService;

	@Value("${rules_directory}")
	private String rulesDirectory;

	/**
	 * Create a new Rule from a given Json
	 *
	 * @param json
	 * @return {@link Rule}
	 */
	@Transactional
	public Rule addRuleFromJson(final String json) {
		System.out.println("CONDITION=" + conditionService.parseRestrictions(json));
		throw new UnsupportedOperationException("RulesService#addRuleFromJson(...) : Not implemented Yet!");
	}
}

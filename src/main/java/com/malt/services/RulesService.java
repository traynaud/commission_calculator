package com.malt.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

	private static final String RULE_FILE_EXTENSION = ".rule";

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private ConditionService conditionService;

	@Value("${rules_directory}")
	private String rulesDirectory;

	/**
	 * Create a new Rule from a given Json<br/>
	 * Since the name is unique, if a rule with a similar name already exists in the
	 * Database, it will be returned
	 *
	 * @param json
	 * @return {@link Rule}
	 */
	@Transactional
	public Rule parseRuleFromJson(final String json) {
		// TODO: Check if the rule exists based on its name
		final Optional<Rule> opt_rule = getRuleByName("TODO");
		if (opt_rule.isPresent()) {
			return opt_rule.get();
		}

		System.out.println("CONDITION=" + conditionService.parseRestrictions(json));
		throw new UnsupportedOperationException("RulesService#addRuleFromJson(...) : Not implemented Yet!");
	}

	/**
	 * Find a rule based on its unique id
	 *
	 * @param name (String) The id of the rule to find
	 * @return {@link Rule}
	 */
	@Transactional(readOnly = true)
	public Optional<Rule> getRuleById(final Long id) {
		return ruleRepository.findById(id);
	}

	/**
	 * Find a rule based on its unique Name
	 *
	 * @param name (String) The name of the rule to find
	 * @return {@link Rule}
	 */
	@Transactional(readOnly = true)
	public Optional<Rule> getRuleByName(final String name) {
		return ruleRepository.findByName(name);
	}

	/**
	 * Get all the rules that are actually stored in the Database
	 *
	 * @return {@link Rule}
	 */
	@Transactional(readOnly = true)
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}

	/**
	 * Parse all {@link Rule}s present in the Configuration directory
	 *
	 * @return {@link Rule}s
	 * @throws IOException
	 * @see #rulesDirectory
	 */
	@Transactional
	public List<Rule> parseDirectory() throws IOException {
		final Path path = Paths.get(rulesDirectory);
		if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
			return parseDirectory(path);
		}
		logger.warn("The Rule directory '{}' does not exist or is unreachable!", rulesDirectory);
		return null;
	}

	/**
	 * Parse all {@link Rule}s present in the specified directory
	 *
	 * @param directory a {@link Path} to a directory
	 * @return {@link Rule}s
	 * @throws IOException
	 */
	@Transactional
	public List<Rule> parseDirectory(final Path directory) throws IOException {
		final Set<Path> files = Files.list(directory)
				.filter(file -> file.getFileName().toString().endsWith(RULE_FILE_EXTENSION))
				.collect(Collectors.toSet());
		final List<Rule> rules = new ArrayList<>(files.size());
		for (final Path path : files) {
			try {
				rules.add(parseFile(path));
			} catch (final IOException e) {
				logger.error("An IO error occured with file : {}\n\t>{}: {}", path.toAbsolutePath(),
						e.getClass().getSimpleName(), e.getMessage());
			}
		}
		return rules;
	}

	/**
	 * Parse a single {@link Rule} file stored in Json
	 *
	 * @param file a {@link Path} to the Json File
	 * @return {@link Rule}
	 * @throws IOException
	 */
	@Transactional
	public Rule parseFile(final Path file) throws IOException {
		final StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.toFile()), "UTF-8"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.startsWith("//")) {
					continue;
				}
				content.append(line).append("\n");
			}
		}
		return parseRuleFromJson(content.toString());
	}
}

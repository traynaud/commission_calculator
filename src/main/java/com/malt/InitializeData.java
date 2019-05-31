package com.malt;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.Rule;
import com.malt.model.condition.AndCondition;
import com.malt.model.condition.Condition;
import com.malt.model.condition.DateTimeCondition;
import com.malt.model.condition.DelayCondition;
import com.malt.model.condition.LocationCondition;
import com.malt.model.condition.NumericalDoubleCondition;
import com.malt.model.condition.NumericalIntegerCondition;
import com.malt.model.condition.OrCondition;
import com.malt.model.condition.StringCondition;
import com.malt.model.condition.enums.DateTimeOperator;
import com.malt.model.condition.enums.LocationOperator;
import com.malt.model.condition.enums.NumericalOperator;
import com.malt.model.condition.enums.StringOperator;
import com.malt.model.enums.Country;
import com.malt.model.enums.Parameter;
import com.malt.model.enums.Time;
import com.malt.repositories.AndConditionRepository;
import com.malt.repositories.DateTimeConditionRepository;
import com.malt.repositories.DelayConditionRepository;
import com.malt.repositories.DelayRepository;
import com.malt.repositories.LocationConditionRepository;
import com.malt.repositories.LocationRepository;
import com.malt.repositories.NumericalDoubleConditionRepository;
import com.malt.repositories.NumericalIntegerConditionRepository;
import com.malt.repositories.OrConditionRepository;
import com.malt.repositories.RuleRepository;
import com.malt.repositories.StringConditionRepository;

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
	RuleRepository ruleRepository;
	@Autowired
	DelayRepository delayRepository;
	@Autowired
	LocationRepository locationRepository;

	@Autowired
	StringConditionRepository stringConditionRepository;

	@Autowired
	LocationConditionRepository locationConditionRepository;

	@Autowired
	DateTimeConditionRepository dateTimeConditionRepository;

	@Autowired
	NumericalDoubleConditionRepository numericalDoubleConditionRepository;
	@Autowired
	NumericalIntegerConditionRepository numericalIntegerConditionRepository;
	@Autowired
	DelayConditionRepository numericalDelayConditionRepository;

	@Autowired
	OrConditionRepository orConditionRepository;

	@Autowired
	AndConditionRepository andConditionRepository;

	@Transactional
	@Override
	public void run(final String... args) throws Exception {
		Rule rule1 = new Rule();
		rule1.setName("Test 4");
		rule1.setRate(10);
		NumericalDoubleCondition condition1 = new NumericalDoubleCondition();
		condition1.setName(Parameter.CLIENT_LOCATION);
		condition1.getOperators().put(NumericalOperator.GREATER_THAN, 12.8);
		condition1 = numericalDoubleConditionRepository.save(condition1);
		rule1.setCondition(condition1);
		rule1 = ruleRepository.save(rule1);
		logger.info("Creation new Rule with id = {}}", rule1.getId());

		Rule rule2 = new Rule();
		rule2.setName("Test 5");
		rule2.setRate(10);
		NumericalIntegerCondition condition2 = new NumericalIntegerCondition();
		condition2.setName(Parameter.CLIENT_LOCATION);
		condition2.getOperators().put(NumericalOperator.GREATER_THAN, 12);
		condition2 = numericalIntegerConditionRepository.save(condition2);
		rule2.setCondition(condition2);
		rule2 = ruleRepository.save(rule2);
		logger.info("Creation new Rule with id = {}}", rule2.getId());

		Rule rule3 = new Rule();
		rule3.setName("Test 6");
		rule3.setRate(10);
		Delay delay = new Delay();
		delay.addTime(Time.SECOND, 12);
		delay = delayRepository.save(delay);
		DelayCondition condition3 = new DelayCondition();
		condition3.setName(Parameter.CLIENT_LOCATION);
		condition3.getOperators().put(NumericalOperator.GREATER_THAN, delay);
		condition3 = numericalDelayConditionRepository.save(condition3);
		rule3.setCondition(condition3);
		rule3 = ruleRepository.save(rule3);
		logger.info("Creation new Rule with id = {}}", rule3.getId());

		Rule rule4 = new Rule();
		rule4.setName("Test");
		rule4.setRate(10);
		StringCondition condition4 = new StringCondition();
		condition4.setName(Parameter.CLIENT_LOCATION);
		condition4.getOperators().put(StringOperator.EQUALS, "Test");
		condition4 = stringConditionRepository.save(condition4);
		rule4.setCondition(condition4);
		rule4 = ruleRepository.save(rule4);
		logger.info("Creation new Rule with id = {}}", rule4.getId());

		Rule rule5 = new Rule();
		rule5.setName("Test2");
		rule5.setRate(10);
		Location location = new Location();
		location.setCountry(Country.FRANCE);
		location = locationRepository.save(location);
		LocationCondition condition5 = new LocationCondition();
		condition5.setName(Parameter.CLIENT_LOCATION);
		condition5.getOperators().put(LocationOperator.IN_COUNTRY, location);
		condition5 = locationConditionRepository.save(condition5);
		rule5.setCondition(condition5);
		rule5 = ruleRepository.save(rule5);
		logger.info("Creation new Rule with id = {}}", rule5.getId());
		logger.info("Location: Country = {}, Continent={}", location.getCountry(), location.getContinent());

		Rule rule6 = new Rule();
		rule6.setName("Test3");
		rule6.setRate(10);
		DateTimeCondition condition6 = new DateTimeCondition();
		condition6.setName(Parameter.CLIENT_LOCATION);
		condition6.getOperators().put(DateTimeOperator.AFTER, OffsetDateTime.now());
		condition6 = dateTimeConditionRepository.save(condition6);
		rule6.setCondition(condition6);
		rule6 = ruleRepository.save(rule6);
		logger.info("Creation new Rule with id = {}}", rule6.getId());

		Rule rule7 = new Rule();
		rule7.setName("Rule OR");
		rule7.setRate(10);
		final Set<Condition> conditionsOR = new HashSet<>();
		conditionsOR.add(condition1);
		conditionsOR.add(condition6);
		OrCondition condition7 = new OrCondition();
		condition7.setConditions(conditionsOR);
		condition7 = orConditionRepository.save(condition7);
		rule7.setCondition(condition7);
		rule7 = ruleRepository.save(rule7);
		logger.info("Creation new Rule with id = {}}", rule7.getId());

		Rule rule8 = new Rule();
		rule8.setName("Rule AND");
		rule8.setRate(10);
		final Set<Condition> conditionsAnd = new HashSet<>();
		conditionsAnd.add(condition3);
		conditionsAnd.add(condition7);
		AndCondition condition8 = new AndCondition();
		condition8.setConditions(conditionsAnd);
		condition8 = andConditionRepository.save(condition8);
		rule8.setCondition(condition8);
		rule8 = ruleRepository.save(rule8);
		logger.info("Creation new Rule with id = {}}", rule8.getId());
	}
}

package com.malt;

import java.time.OffsetDateTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Location;
import com.malt.model.Rule;
import com.malt.model.condition.DateTimeCondition;
import com.malt.model.condition.LocationCondition;
import com.malt.model.condition.StringCondition;
import com.malt.model.condition.enums.DateTimeOperator;
import com.malt.model.condition.enums.LocationOperator;
import com.malt.model.condition.enums.StringOperator;
import com.malt.model.enums.Country;
import com.malt.model.enums.Parameter;
import com.malt.repositories.DateTimeConditionRepository;
import com.malt.repositories.LocationConditionRepository;
import com.malt.repositories.RuleRepository;
import com.malt.repositories.StringConditionRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataBaseTest {

	private static final Logger logger = LoggerFactory.getLogger(DataBaseTest.class);

	@Autowired
	RuleRepository ruleRepository;

	@Autowired
	StringConditionRepository stringConditionRepository;

	@Autowired
	LocationConditionRepository locationConditionRepository;

	@Autowired
	DateTimeConditionRepository dateTimeConditionRepository;

//	@Autowired
//	NumericalConditionRepository numericalConditionRepository;

	@Transactional
	@Test
	public void SimpleRuletest() {
		Rule rule1 = new Rule();
		rule1.setName("Test");
		rule1.setRate(10);
		StringCondition condition1 = new StringCondition();
		condition1.setName(Parameter.CLIENT_LOCATION);
		condition1.getOperators().put(StringOperator.EQUALS, "Test");
		condition1 = stringConditionRepository.save(condition1);
		rule1.setCondition(condition1);
		rule1 = ruleRepository.save(rule1);
		logger.info("Creation new Rule with id = {}}", rule1.getId());

		Rule rule2 = new Rule();
		rule2.setName("Test2");
		rule2.setRate(10);
		final Location location = new Location();
		location.setCountry(Country.FRANCE);
		LocationCondition condition2 = new LocationCondition();
		condition2.setName(Parameter.CLIENT_LOCATION);
		condition2.getOperators().put(LocationOperator.IN_COUNTRY, location);
		condition2 = locationConditionRepository.save(condition2);
		rule2.setCondition(condition2);
		rule2 = ruleRepository.save(rule2);
		logger.info("Creation new Rule with id = {}}", rule2.getId());

		Rule rule3 = new Rule();
		rule3.setName("Test3");
		rule3.setRate(10);
		DateTimeCondition condition3 = new DateTimeCondition();
		condition3.setName(Parameter.CLIENT_LOCATION);
		condition3.getOperators().put(DateTimeOperator.AFTER, OffsetDateTime.now());
		condition3 = dateTimeConditionRepository.save(condition3);
		rule3.setCondition(condition3);
		rule3 = ruleRepository.save(rule3);
		logger.info("Creation new Rule with id = {}}", rule3.getId());
	}

//	@Transactional
//	@Test
//	public void NumericalRuletest() {
//		Rule rule1 = new Rule();
//		rule1.setName("Test 4");
//		rule1.setRate(10);
//		NumericalCondition<Double> condition1 = new NumericalCondition<>();
//		condition1.setName(Parameter.CLIENT_LOCATION);
//		condition1.getOperators().put(NumericalOperator.GREATER_THAN, 12.8);
//		condition1 = numericalConditionRepository.save(condition1);
//		rule1.setCondition(condition1);
//		rule1 = ruleRepository.save(rule1);
//		logger.info("Creation new Rule with id = {}}", rule1.getId());
//
//		Rule rule2 = new Rule();
//		rule2.setName("Test 5");
//		rule2.setRate(10);
//		NumericalCondition<Integer> condition2 = new NumericalCondition<>();
//		condition2.setName(Parameter.CLIENT_LOCATION);
//		condition2.getOperators().put(NumericalOperator.GREATER_THAN, 12);
//		condition2 = numericalConditionRepository.save(condition2);
//		rule2.setCondition(condition2);
//		rule2 = ruleRepository.save(rule2);
//		logger.info("Creation new Rule with id = {}}", rule2.getId());
//
//		Rule rule3 = new Rule();
//		rule3.setName("Test 6");
//		rule3.setRate(10);
//		final Delay delay = new Delay();
//		delay.addTime(Time.SECOND, 12);
//		NumericalCondition<Delay> condition3 = new NumericalCondition<>();
//		condition3.setName(Parameter.CLIENT_LOCATION);
//		condition3.getOperators().put(NumericalOperator.GREATER_THAN, delay);
//		condition3 = numericalConditionRepository.save(condition3);
//		rule3.setCondition(condition3);
//		rule3 = ruleRepository.save(rule3);
//		logger.info("Creation new Rule with id = {}}", rule3.getId());
//	}
}

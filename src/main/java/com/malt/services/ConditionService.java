package com.malt.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Null;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.exceptions.InvalidQueryException;
import com.malt.model.Delay;
import com.malt.model.condition.AndCondition;
import com.malt.model.condition.Condition;
import com.malt.model.condition.DelayCondition;
import com.malt.model.condition.OrCondition;
import com.malt.model.condition.enums.NumericalOperator;
import com.malt.model.condition.enums.Operator;
import com.malt.model.enums.Parameter;
import com.malt.model.enums.ParameterType;
import com.malt.repositories.AndConditionRepository;
import com.malt.repositories.DateTimeConditionRepository;
import com.malt.repositories.DelayConditionRepository;
import com.malt.repositories.LocationConditionRepository;
import com.malt.repositories.NumericalDoubleConditionRepository;
import com.malt.repositories.NumericalIntegerConditionRepository;
import com.malt.repositories.OrConditionRepository;
import com.malt.repositories.StringConditionRepository;
import com.mysql.cj.xdevapi.JsonArray;

/**
 * Service to manage everthing related to Conditions
 *
 * @author Tanguy
 * @version 1.0
 * @since 31 May 2019
 *
 */
@Service
public class ConditionService {

	private static final Logger logger = LoggerFactory.getLogger(ConditionService.class);

	@Autowired
	private StringConditionRepository stringConditionRepository;

	@Autowired
	private LocationConditionRepository locationConditionRepository;

	@Autowired
	private DateTimeConditionRepository dateTimeConditionRepository;

	@Autowired
	private NumericalDoubleConditionRepository numericalDoubleConditionRepository;

	@Autowired
	private NumericalIntegerConditionRepository numericalIntegerConditionRepository;

	@Autowired
	private DelayConditionRepository delayConditionRepository;

	@Autowired
	private OrConditionRepository orConditionRepository;

	@Autowired
	private AndConditionRepository andConditionRepository;

	/**
	 * Parse Json to extract the restrictions that compose the conditions of the
	 * rule
	 *
	 * @param jsonStr
	 * @return {@link Condition}
	 */
	@Transactional
	public Condition parseRestrictions(final String jsonStr) {
		try {
			JSONObject json = new JSONObject(jsonStr);
			if (!json.isNull("restrictions")) {
				json = json.getJSONObject("restrictions");
			}
			return recursiveConditionBuilder(null, json);
		} catch (final JSONException e) {
			logger.error("Unable to parse Json: " + e.getClass().getSimpleName() + ": " + e.getMessage());
			throw new InvalidQueryException(
					"Specified Json can't be pocessed: " + e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

	/**
	 * Determine if the received object is an {@link Operator} or a
	 * {@link Parameter} and call the appropriate function
	 *
	 * @param name (String) the name of the Json object, {@link Null} is possible
	 *             for anonymous arrays or objects
	 * @param json a {@link JSONObject} or a {@link JSONArray}
	 * @return {@link Condition} a condition built based on the content of the Json
	 */
	private Condition recursiveConditionBuilder(final String name, final Object json) {
		System.out.println(name + "->" + json);
		if (name == null) {
			if (json instanceof JSONObject) {
				final JSONObject jsonObject = (JSONObject) json;
				if (jsonObject.length() > 1) {
					return buildOperator(Operator.AND, json);
				} else {
					final String key = jsonObject.keys().next();
					final Object content = getContent(key, jsonObject);
					if (content == null) {
						return null;
					}
					return recursiveConditionBuilder(key, content);
				}
			} else if (json instanceof JSONArray) {
				return buildOperator(Operator.AND, json);
			} else {
				logger.warn("The Json type '{}' is not supported yet and will be ignored",
						json.getClass().getSimpleName());
				return null;
			}
		} else {
			final Operator operator = Operator.fromString(name);
			if (operator != null) {
				return buildOperator(operator, json);
			}
			final Parameter parameter = Parameter.fromString(name);
			if (parameter != null) {
				return buildParameter(parameter, json);
			}
			logger.warn("Unknown Operator / Parameter : '{}' will be ignored!", name);
			return null;
		}
	}

	/**
	 * Build an non terminal {@link Condition} based on the specified Operator
	 *
	 * @param operator An {@link Operator}
	 * @param json     a {@link JSONObject} or a {@link JSONArray}
	 * @return {@link Condition}
	 */
	private Condition buildOperator(final Operator operator, final Object json) {
		if (!(json instanceof JSONObject) && !(json instanceof JSONArray)) {
			logger.warn("The Json type '{}' is not supported yet and will be ignored", json.getClass().getSimpleName());
			return null;
		}
		final Set<Condition> conditions = new HashSet<>();
		if (json instanceof JSONObject) {
			final JSONObject jsonObject = (JSONObject) json;
			for (final String key : jsonObject.keySet()) {
				final Object content = getContent(key, jsonObject);
				if (content != null) {
					final Condition condition = recursiveConditionBuilder(key, content);
					if (condition != null) {
						conditions.add(condition);
					}
				}
			}
		} else if (json instanceof JSONArray) {
			final JSONArray jsonArray = (JSONArray) json;
			for (final Object object : jsonArray) {
				final Condition condition = recursiveConditionBuilder(null, object);
				if (condition != null) {
					conditions.add(condition);
				}
			}
		}
		switch (operator) {
		case AND:
			final AndCondition andCondition = new AndCondition();
			andCondition.setConditions(conditions);
			// andCondition = andConditionRepository.save(andCondition);
			return andCondition;
		case OR:
			final OrCondition orCondition = new OrCondition();
			orCondition.setConditions(conditions);
			// orCondition = orConditionRepository.save(orCondition);
			return orCondition;
		default:
			logger.warn("The operator '@{}' is not implemented yet and will be ignored", operator.getOperator());
			return null;
		}
	}

	/**
	 * Build a terminal {@link Condition}, based on its {@link ParameterType}
	 *
	 * @param parameter ({@link Parameter}) an explicit parameter
	 * @param json      a {@link JSONObject} or a {@link JSONArray}
	 * @return {@link Condition}
	 */
	private Condition buildParameter(final Parameter parameter, final Object json) {
		if (!(json instanceof JSONObject) && !(json instanceof JSONArray)) {
			logger.warn("The Json type '{}' is not supported yet and will be ignored", json.getClass().getSimpleName());
			return null;
		}
		final Map<String, String> parameterValues = extractParameterValues(json);
		switch (parameter.getType()) {
		case DELAY:
			final DelayCondition delayCondition = new DelayCondition();
			final Map<NumericalOperator, Delay> operators = delayCondition.getOperators();
			for (final String key : parameterValues.keySet()) {
				final NumericalOperator operator = NumericalOperator.fromString(key);
				if (key == null) {
					logger.warn("Unknown {} operator : '{}'. The operator will be ignored!", parameter.getType().name(),
							key);
					continue;
				}
				final Delay delay = Delay.fromString(parameterValues.get(key));
				if (delay != null) {
					operators.put(operator, delay);
				}
			}
			if (operators.isEmpty()) {
				return null;
			}
			// delayCondition = delayConditionRepository.save(delayCondition);
			System.out.println("DELAY:" + operators);
			return delayCondition;
		default:
			logger.warn("The Parameter '@{}' is not implemented yet and will be ignored", parameter.getIdentifier());
			return null;
		}
	}

	private Map<String, String> extractParameterValues(final Object json) {
		final Map<String, String> values = new HashMap<>();
		if (json instanceof JSONObject) {
			final JSONObject jsonObject = (JSONObject) json;
			for (final String key : jsonObject.keySet()) {
				final String value = jsonObject.optString(key);
				if (value != null) {
					values.put(key, value);
				}
			}
		} else if (json instanceof JSONArray) {
			final JSONArray jsonArray = (JSONArray) json;
			for (final Object object : jsonArray) {
				if (object instanceof JSONObject) {
					final JSONObject jsonObject = (JSONObject) object;
					for (final String key : jsonObject.keySet()) {
						final String value = jsonObject.optString(key);
						if (value != null) {
							values.put(key, value);
						}
					}
				}
			}
		}
		return values;
	}

	/**
	 * Get into the specified {@link JSONObject} the content of the corresponding
	 * key, and return if as a {@link JSONObject} or a {@link JsonArray} depending
	 * of its nature
	 *
	 * @param name
	 * @param json
	 * @return
	 */
	private Object getContent(final String name, final JSONObject json) {
		if (!json.has(name)) {
			return null;
		}
		final JSONObject object = json.optJSONObject(name);
		if (object != null) {
			return object;
		}
		final JSONArray array = json.optJSONArray(name);
		if (array != null) {
			return array;
		}
		logger.warn("The Json type '{}' is not supported yet and will be ignored", json.getClass().getSimpleName());
		return null;
	}

	public static void main(final String[] args) {
		//@formatter:off
		final String json = "{  \r\n" +
				"   id:1,\r\n" +
				"   name:'spain or repeat',\r\n" +
				"   rate:{  \r\n" +
				"      percent:8\r\n" +
				"   },\r\n" +
				"   restrictions:{  \r\n" +
				"      @or:[  \r\n" +
				"         {  \r\n" +
				"            '@mission.duration ':{  \r\n" +
				"               gt:'2months'\r\n" +
				"            }\r\n" +
				"         },\r\n" +
				"         {  \r\n" +
				"            '@commercialrelation.duration':{  \r\n" +
				"               gt:'2months'\r\n" +
				"            },\r\n" +
				"\r\n" +
				"         }\r\n" +
				"      ],\r\n" +
				"      '@client.location':{  \r\n" +
				"         country:'ES'\r\n" +
				"      },\r\n" +
				"      '@freelancer.location':{  \r\n" +
				"         country:'ES'\r\n" +
				"      }\r\n" +
				"   }\r\n" +
				"}";
		//@formatter:on
		final ConditionService service = new ConditionService();
		System.out.println(service.parseRestrictions(json));
	}
}

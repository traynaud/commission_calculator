package com.malt.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malt.exceptions.InvalidQueryException;
import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.Rule;
import com.malt.model.condition.AndCondition;
import com.malt.model.condition.Condition;
import com.malt.model.condition.DelayCondition;
import com.malt.model.condition.LocationCondition;
import com.malt.model.condition.OrCondition;
import com.malt.model.condition.ValueCondition;
import com.malt.model.dtos.CommissionAnswerDTO;
import com.malt.model.dtos.CommissionRequestDTO;
import com.malt.model.enums.Country;
import com.malt.model.json.CommercialRelation;
import com.malt.model.json.enums.ClientAttribute;
import com.malt.model.json.enums.CommercialRelationAttribute;
import com.malt.model.json.enums.FreelancerAttribute;
import com.malt.model.json.enums.MissionAttribute;

/**
 * This {@link Service} manage the commissions fees of the freelancers
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Service
public class CommissionService {

	private static final Logger logger = LoggerFactory.getLogger(CommissionService.class);

	private static final double DEFAULT_COMISSION = 10.0;

	@Autowired
	private LocalisationService localisationService;

	@Autowired
	private RulesService ruleService;

	private CommissionAnswerDTO cache_defaultCommission = null;

	/**
	 * Find the commission due based on available rules
	 *
	 * @param json
	 * @return
	 */
	public CommissionAnswerDTO getCommissionFromJson(final String json) {
		try {
			final JSONObject jsonObject = new JSONObject(json);
			final CommissionRequestDTO request = CommissionRequestDTO.fromJson(jsonObject);
			System.out.println(request);
			if (request == null) {
				throw new InvalidQueryException("Unable to parse specified Json Query");
			}
			return findBestRule(request);
		} catch (final JSONException e) {
			logger.error("Unable to parse Json request! Default value will be used!");
			return getDefaultCommission();
		}
	}

	/**
	 * Check all availabe {@link Rule}s and find the most appropriate one<br/>
	 * If many {@link Rule}s apply, the most interesting is the one with the lower
	 * fee<br/>
	 * If none Apply, the Default fee is returned
	 *
	 * @param request ({@link CommercialRelation}) A list of parameters
	 * @return ({@link CommissionAnswerDTO}) the most appropriate fee
	 */
	private CommissionAnswerDTO findBestRule(final CommissionRequestDTO request) {
		final List<Rule> rules = ruleService.getAllRules();
		Rule best = null;
		for (final Rule rule : rules) {
			if (checkOneRule(rule, request) && (best == null || rule.compareTo(best) < 0)) {
				best = rule;
			}
		}
		if (best == null) {
			logger.info("Ro Rule matched the specified query!");
			return getDefaultCommission();
		}
		final CommissionAnswerDTO answer = new CommissionAnswerDTO();
		answer.setFee(best.getRate());
		answer.setReason(best.getName());
		return answer;
	}

	/**
	 * Determine wether or not a {@link Rule} can be applied based on a list of
	 * parameters
	 *
	 * @param rule    a {@link Rule} to check
	 * @param request ({@link CommercialRelation}) A list of parameters
	 * @return <code>true</code> if the {@link Rule} can be applied,
	 *         <code>false</code> otherwise
	 */
	private boolean checkOneRule(final Rule rule, final CommissionRequestDTO request) {
		return recursiveCheckCondition(rule.getCondition(), request);
	}

	/**
	 * Recursively explore the tree of conditions
	 *
	 * @param condition {@link Condition}
	 * @param request   {@link CommissionRequestDTO}
	 * @return <code>true</code> is the condition is validated by the parameters,
	 *         <code>false</code> otherwise
	 */
	private boolean recursiveCheckCondition(final Condition condition, final CommissionRequestDTO request) {
		if (condition instanceof AndCondition) {
			final AndCondition andCondition = (AndCondition) condition;
			for (final Condition sub_condition : andCondition.getConditions()) {
				if (!recursiveCheckCondition(sub_condition, request)) {
					return false;
				}
			}
			return true;
		} else if (condition instanceof OrCondition) {
			final OrCondition orCondition = (OrCondition) condition;
			for (final Condition sub_condition : orCondition.getConditions()) {
				if (recursiveCheckCondition(sub_condition, request)) {
					return true;
				}
			}
			return false;
		} else if (condition instanceof ValueCondition) {
			final boolean value = checkValueCondition((ValueCondition) condition, request);
			System.out.println(((ValueCondition) condition).getName() + ": " + value);
			return value;
		} else {
			logger.info("Unsupported Contition type : {}", condition.getClass().getSimpleName());
			return false;
		}
	}

	/**
	 * Lower Level : Check if the condition is respected or not<br/>
	 * This function is quite long, but the manual mapping is somehow necessary!
	 *
	 * @param condition {@link ValueCondition}
	 * @param request   {@link CommissionRequestDTO}
	 * @return <code>true</code> is the condition is validated by the parameters,
	 *         <code>false</code> otherwise
	 */
	private boolean checkValueCondition(final ValueCondition condition, final CommissionRequestDTO request) {
		switch (condition.getName()) {
		case CLIENT_LOCATION:
			if (request.getClient() == null) {
				return false;
			}
			final String clientIp = request.getClient().getString(ClientAttribute.IP);
			if (clientIp == null) {
				return false;
			}
			final Country clientCountry = localisationService.getCountryFromAddress(clientIp);
			if (clientCountry == null) {
				logger.warn(
						"Unable to determine country of client from IP '{}' : Condition '{}' is rejected by default!",
						clientIp, condition.getName());
				return false;
			}
			final Location clientLocation = localisationService.getLocation(clientCountry);
			if (clientLocation == null) {
				logger.warn(
						"Client location could not be resolved from country {}! Condition '{}' is rejected by default!",
						clientCountry.name(), condition.getName());
				return false;
			}
			if (!(condition instanceof LocationCondition)) {
				logger.warn("Condition '{}' was expected to be a {}! the condition is rejected by default!",
						condition.getName(), LocationCondition.class.getSimpleName());
				return false;
			}
			return ((LocationCondition) condition).check(clientLocation);
		case FREELANCER_LOCATION:
			if (request.getFreelancer() == null) {
				return false;
			}
			final String freelancerIp = request.getFreelancer().getString(FreelancerAttribute.IP);
			if (freelancerIp == null) {
				return false;
			}
			final Country freelancerCountry = localisationService.getCountryFromAddress(freelancerIp);
			if (freelancerCountry == null) {
				logger.warn(
						"Unable to determine country of freelancer from IP '{}' : Condition '{}' is rejected by default!",
						freelancerIp, condition.getName());
				return false;
			}
			final Location freelancerLocation = localisationService.getLocation(freelancerCountry);
			if (freelancerLocation == null) {
				logger.warn(
						"Freelancer location could not be resolved from country {}! Condition '{}' is rejected by default!",
						freelancerCountry.name(), condition.getName());
				return false;
			}
			if (!(condition instanceof LocationCondition)) {
				logger.warn("Condition '{}' was expected to be a {}! the condition is rejected by default!",
						condition.getName(), LocationCondition.class.getSimpleName());
				return false;
			}
			return ((LocationCondition) condition).check(freelancerLocation);
		case MISSION_DURATION:
			if (request.getMission() == null) {
				return false;
			}
			final Delay missionLenght = request.getMission().getDelay(MissionAttribute.LENGTH);
			if (missionLenght == null) {
				return false;
			}
			if (!(condition instanceof DelayCondition)) {
				logger.warn("Condition '{}' was expected to be a {}! the condition is rejected by default!",
						condition.getName(), DelayCondition.class.getSimpleName());
				return false;
			}
			return ((DelayCondition) condition).check(missionLenght);
		case COMMERCIAL_RELATION_DURATION:
			if (request.getCommercialRelation() == null) {
				return false;
			}
			final OffsetDateTime commercialRelationFirstMission = request.getCommercialRelation()
					.getDateTime(CommercialRelationAttribute.FIRST_MISSION);
			if (commercialRelationFirstMission == null) {
				return false;
			}
			final OffsetDateTime commercialRelationLastMission = request.getCommercialRelation()
					.getDateTime(CommercialRelationAttribute.LAST_MISSION);
			if (commercialRelationLastMission == null) {
				return false;
			}
			final Delay commercialRelationDelay = Delay.delayBetween(commercialRelationFirstMission,
					commercialRelationLastMission);
			if (commercialRelationDelay == null) {
				logger.warn(
						"Unexpectedly unable to compute a delay between {} and {}: Condition {} will be rejected by default",
						commercialRelationFirstMission, commercialRelationLastMission, condition.getName());
			}
			if (!(condition instanceof DelayCondition)) {
				logger.warn("Condition '{}' was expected to be a {}! The condition is rejected by default!",
						condition.getName(), DelayCondition.class.getSimpleName());
				return false;
			}
			return ((DelayCondition) condition).check(commercialRelationDelay);
		default:
			logger.info("Parameter '{}' is not supported Yet!", condition.getName());
			return false;
		}

	}

	/**
	 * Return or Build the default {@link CommissionAnswerDTO}
	 *
	 * @return {@link CommissionAnswerDTO}
	 */
	private CommissionAnswerDTO getDefaultCommission() {
		if (cache_defaultCommission == null) {
			cache_defaultCommission = new CommissionAnswerDTO();
			cache_defaultCommission.setFee(DEFAULT_COMISSION);
			cache_defaultCommission.setReason("DEFAULT");
		}
		return cache_defaultCommission;
	}

}

package com.malt.services;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malt.exceptions.InvalidQueryException;
import com.malt.model.Rule;
import com.malt.model.dtos.CommissionAnswerDTO;
import com.malt.model.json.CommercialRelation;

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
			final CommercialRelation request = CommercialRelation.parseJson(jsonObject);
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
	private CommissionAnswerDTO findBestRule(final CommercialRelation request) {
		final List<Rule> rules = ruleService.getAllRules();
		// TODO: Check only rules with a better fee to optimize exploration
		throw new UnsupportedOperationException("CommissionService#findBestRule Not implemented yet!");
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
	private boolean checkOneRule(final Rule rule, final CommercialRelation request) {
		// TODO: Check One Rule
		throw new UnsupportedOperationException("CommissionService#checkOneRule Not implemented yet!");
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

package com.malt.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private LocalisationService localisationService;

}

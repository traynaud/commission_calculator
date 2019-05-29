package com.malt.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malt.services.CommissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This {@link Controller} manage the commissions fees of the freelancers
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Api("This Controller manage the configuration requests")
@RestController
@RequestMapping(value = "/commission")
@CrossOrigin
public class CommissionController {

	private static final Logger logger = LoggerFactory.getLogger(CommissionController.class);

	@Autowired
	private CommissionService comissionService;

	@ApiOperation(value = "Compute the freelancer commission based on available parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The commission fee"),
			@ApiResponse(code = 400, message = "The received request is invalid and can't be processed!"),
			@ApiResponse(code = 500, message = "An internal server error occured"), })
	@PostMapping(value = "/compute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addConfiguration() {
		// TODO
		logger.error("Compute comission fee : Not Implemented Yet!");
		throw new UnsupportedOperationException("Compute comission fee : Not Implemented Yet!");
	}
}

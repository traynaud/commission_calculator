package com.malt.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malt.model.Rule;
import com.malt.services.RulesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This {@link Controller} the requests that deal with rules
 *
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@Api("This Controller manage the requests that deal with rules")
@RestController
@RequestMapping(value = "/rules")
@CrossOrigin
public class RulesController {

	private static final Logger logger = LoggerFactory.getLogger(RulesController.class);

	@Autowired
	private RulesService rulesService;

	@ApiOperation(value = "Add a new configuration rule to compute freelancers commission")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The new commission rule"),
			@ApiResponse(code = 400, message = "The received request is invalid and can't be processed!"),
			@ApiResponse(code = 500, message = "An internal server error occured"), })
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Rule add(@RequestBody final String body) {
		return rulesService.addRuleFromJson(body);
	}

	@ApiOperation(value = "List all existing rules that are used to compute freelancer commissions")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The exhaustive list of commission rules"),
			@ApiResponse(code = 400, message = "The received request is invalid and can't be processed!"),
			@ApiResponse(code = 500, message = "An internal server error occured"), })
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public void list() {
		// TODO
		logger.error("list all rules : Not Implemented Yet!");
		throw new UnsupportedOperationException("List all rules : Not Implemented Yet!");
	}
}

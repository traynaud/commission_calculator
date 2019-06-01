package com.malt.model.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Commission
 * Answer<br/>
 * It can be easily improved if necessary
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class CommissionAnswerDTO {

	double fee;

	String reason;
}

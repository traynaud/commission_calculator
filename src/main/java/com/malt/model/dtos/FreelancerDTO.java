package com.malt.model.dtos;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a freelancer<br/>
 * Currently basic but can be easily improved depending on needs
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class FreelancerDTO {

	String ip;

	/**
	 * Create an object based on a Json object
	 *
	 * @param jsonObject
	 * @return
	 */
	public static FreelancerDTO fromJson(final JSONObject jsonObject) {
		try {
			final FreelancerDTO dto = new FreelancerDTO();
			if (jsonObject.has("ip")) {
				dto.setIp(jsonObject.getString("ip"));
			} else {
				return null;
			}
			return dto;
		} catch (final JSONException e) {
			return null;
		}
	}
}

package com.malt.model.dtos;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Commission
 * Request<br/>
 * It can be easily improved if necessary
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class CommissionRequestDTO {

	ClientDTO client;

	FreelancerDTO freelancer;

	MissionDTO mission;

	CommercialRelationDTO commercialRelation;

	/**
	 * Create an object based on a Json object
	 *
	 * @param jsonObject
	 * @return
	 */
	public static CommissionRequestDTO fromJson(final JSONObject jsonObject) {
		try {
			final CommissionRequestDTO dto = new CommissionRequestDTO();
			final JSONObject client = jsonObject.optJSONObject("client");
			if (client != null) {
				dto.setClient(ClientDTO.fromJson(client));
			}
			final JSONObject freelancer = jsonObject.optJSONObject("freelancer");
			if (freelancer != null) {
				dto.setFreelancer(FreelancerDTO.fromJson(freelancer));
			}
			final JSONObject mission = jsonObject.optJSONObject("mission");
			if (mission != null) {
				dto.setMission(MissionDTO.fromJson(mission));
			}
			final JSONObject commercialRelation = jsonObject.optJSONObject("commercialrelation");
			if (commercialRelation != null) {
				dto.setCommercialRelation(CommercialRelationDTO.fromJson(commercialRelation));
			}
			return dto;
		} catch (final JSONException e) {
			return null;
		}
	}
}

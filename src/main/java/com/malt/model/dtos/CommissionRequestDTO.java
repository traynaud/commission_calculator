package com.malt.model.dtos;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.json.Client;
import com.malt.model.json.CommercialRelation;
import com.malt.model.json.Freelancer;
import com.malt.model.json.JsonParameter;
import com.malt.model.json.Mission;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Commission
 * Request<br/>
 * It can be easily improved if necessary
 *
 * @author Tanguy
 * @version 1.1
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class CommissionRequestDTO {

	Client client;

	Freelancer freelancer;

	Mission mission;

	CommercialRelation commercialRelation;

	/**
	 * TODO: V2 Full geric with {@link JsonParameter}<br/>
	 * Create an object based on a Json object
	 *
	 * @param jsonObject
	 * @return
	 */
	public static CommissionRequestDTO fromJson(final JSONObject jsonObject) {
		try {
			final CommissionRequestDTO dto = new CommissionRequestDTO();
			final JSONObject clientJson = jsonObject.optJSONObject("client");
			if (clientJson != null) {
				final Client client = new Client();
				try {
					client.parseJson(clientJson);
					dto.setClient(client);
				} catch (final JSONException e) {

				}
			}
			final JSONObject freelancerJson = jsonObject.optJSONObject("freelancer");
			if (freelancerJson != null) {
				final Freelancer freelancer = new Freelancer();
				try {
					freelancer.parseJson(freelancerJson);
					dto.setFreelancer(freelancer);
				} catch (final JSONException e) {

				}
			}
			final JSONObject missionJson = jsonObject.optJSONObject("mission");
			if (missionJson != null) {
				final Mission mission = new Mission();
				try {
					mission.parseJson(missionJson);
					dto.setMission(mission);
				} catch (final JSONException e) {

				}
			}
			final JSONObject commercialRelationJson = jsonObject.optJSONObject("commercialrelation");
			if (commercialRelationJson != null) {
				final CommercialRelation commercialRelation = new CommercialRelation();
				try {
					commercialRelation.parseJson(commercialRelationJson);
					dto.setCommercialRelation(commercialRelation);
				} catch (final JSONException e) {

				}
			}
			return dto;
		} catch (final JSONException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("COMMISSION REQUEST:");
		sb.append("\n\t").append(client != null ? client : "CLIENT=Null");
		sb.append("\n\t").append(freelancer != null ? freelancer : "FREELANCER=Null");
		sb.append("\n\t").append(mission != null ? mission : "MISSION=Null");
		sb.append("\n\t").append(commercialRelation != null ? commercialRelation : "Commercial relation=Null");
		return sb.toString();
	}
}

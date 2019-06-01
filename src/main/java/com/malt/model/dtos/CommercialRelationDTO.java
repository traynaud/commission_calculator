package com.malt.model.dtos;

import java.time.OffsetDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.utils.TimeUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a teh commercia
 * relation between the {@link ClientDTO} and the {@link FreelancerDTO} of a
 * {@link MissionDTO}r<br/>
 * Currently basic but can be easily improved depending on needs
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class CommercialRelationDTO {

	OffsetDateTime firstMission;
	OffsetDateTime lastMission;

	/**
	 * Create an object based on a Json object
	 *
	 * @param jsonObject
	 * @return
	 */
	public static CommercialRelationDTO fromJson(final JSONObject jsonObject) {
		try {
			final CommercialRelationDTO dto = new CommercialRelationDTO();
			if (jsonObject.has("firstmission")) {
				final OffsetDateTime firstMission = TimeUtils
						.parseDateTimeFromString(jsonObject.getString("firstmission"));
				if (firstMission == null) {
					return null;
				}
				dto.setFirstMission(firstMission);
			} else {
				return null;
			}
			if (jsonObject.has("last_mission")) {
				final OffsetDateTime lastMission = TimeUtils
						.parseDateTimeFromString(jsonObject.getString("last_mission"));
				if (lastMission == null) {
					return null;
				}
				dto.setFirstMission(lastMission);
			} else {
				return null;
			}
			return dto;
		} catch (final JSONException e) {
			return null;
		}
	}
}

package com.malt.model.dtos;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.malt.model.Delay;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Mission<br/>
 * Currently basic but can be easily improved depending on needs
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class MissionDTO {

	private static final Logger logger = LoggerFactory.getLogger(MissionDTO.class);

	Delay lenght;

	/**
	 * Create an object based on a Json object
	 *
	 * @param jsonObject
	 * @return
	 */
	public static MissionDTO fromJson(final JSONObject jsonObject) {
		try {
			final MissionDTO dto = new MissionDTO();
			if (jsonObject.has("length")) {
				final Delay delay = Delay.fromString(jsonObject.getString("length"));
				if (delay == null) {
					logger.warn("Unable to create Mission: Invalid delay '{}'", jsonObject.getString("length"));
					return null;
				}
				dto.setLenght(delay);
			} else {
				return null;
			}
			return dto;
		} catch (final JSONException e) {
			return null;
		}
	}
}

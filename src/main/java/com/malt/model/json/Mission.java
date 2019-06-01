package com.malt.model.json;

import java.time.OffsetDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.json.enums.MissionAttribute;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a Mission<br/>
 * V2 uses generic attributes described by {@link MissionAttribute}
 *
 * @author Tanguy
 * @version 2.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class Mission extends JsonParameter {

	public String getString(final MissionAttribute key) {
		return super.getString(key.getKey());
	}

	public Double getDouble(final MissionAttribute key) {
		return super.getDouble(key.getKey());
	}

	public Integer getInteger(final MissionAttribute key) {
		return super.getInteger(key.getKey());
	}

	public Delay getDelay(final MissionAttribute key) {
		return super.getDelay(key.getKey());
	}

	public Location getLocation(final MissionAttribute key) {
		return super.getLocation(key.getKey());
	}

	public OffsetDateTime getDateTime(final MissionAttribute key) {
		return super.getDateTime(key.getKey());
	}

	@Override
	public void parseJson(final JSONObject jsonObject) throws JSONException {
		for (final MissionAttribute attribute : MissionAttribute.values()) {
			if (jsonObject.has(attribute.getKey())) {
				final Object value = extractJsonValue(jsonObject, attribute.getKey(), attribute.getType());
				if (value != null) {
					put(attribute.getKey(), value);
				} else if (attribute.isMandatory()) {
					throw new JSONException("Missing mandatory attribute:" + attribute.getKey());
				}
			} else if (attribute.isMandatory()) {
				throw new JSONException("Missing mandatory attribute:" + attribute.getKey());
			}
		}
	}

	@Override
	public String toString() {
		return ">MISSION" + super.toString();
	}
}

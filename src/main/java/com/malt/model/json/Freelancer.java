package com.malt.model.json;

import java.time.OffsetDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.json.enums.FreelancerAttribute;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a freelancer<br/>
 * V2 uses generic attributes described by {@link FreelancerAttribute}
 *
 * @author Tanguy
 * @version 2.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class Freelancer extends JsonParameter {

	public String getString(final FreelancerAttribute key) {
		return super.getString(key.getKey());
	}

	public Double getDouble(final FreelancerAttribute key) {
		return super.getDouble(key.getKey());
	}

	public Integer getInteger(final FreelancerAttribute key) {
		return super.getInteger(key.getKey());
	}

	public Delay getDelay(final FreelancerAttribute key) {
		return super.getDelay(key.getKey());
	}

	public Location getLocation(final FreelancerAttribute key) {
		return super.getLocation(key.getKey());
	}

	public OffsetDateTime getDateTime(final FreelancerAttribute key) {
		return super.getDateTime(key.getKey());
	}

	@Override
	public void parseJson(final JSONObject jsonObject) throws JSONException {
		for (final FreelancerAttribute attribute : FreelancerAttribute.values()) {
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
		return ">FREELANCER" + super.toString();
	}
}

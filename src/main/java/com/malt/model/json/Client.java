package com.malt.model.json;

import java.time.OffsetDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.json.enums.ClientAttribute;

import lombok.Getter;
import lombok.Setter;

/**
 * Temporary object use to store informations related to a client<br/>
 * V2 uses generic attributes described by {@link ClientAttribute}
 *
 * @author Tanguy
 * @version 2.0
 * @since 01 June 2019
 *
 */
@Getter
@Setter
public class Client extends JsonParameter {

	public String getString(final ClientAttribute key) {
		return super.getString(key.getKey());
	}

	public Double getDouble(final ClientAttribute key) {
		return super.getDouble(key.getKey());
	}

	public Integer getInteger(final ClientAttribute key) {
		return super.getInteger(key.getKey());
	}

	public Delay getDelay(final ClientAttribute key) {
		return super.getDelay(key.getKey());
	}

	public Location getLocation(final ClientAttribute key) {
		return super.getLocation(key.getKey());
	}

	public OffsetDateTime getDateTime(final ClientAttribute key) {
		return super.getDateTime(key.getKey());
	}

	@Override
	public void parseJson(final JSONObject jsonObject) throws JSONException {
		for (final ClientAttribute attribute : ClientAttribute.values()) {
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
		return ">CLIENT" + super.toString();
	}
}

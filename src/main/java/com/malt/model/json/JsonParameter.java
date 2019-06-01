package com.malt.model.json;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.malt.model.Delay;
import com.malt.model.Location;
import com.malt.model.enums.Continent;
import com.malt.model.enums.Country;
import com.malt.model.enums.ParameterType;
import com.malt.utils.TimeUtils;

/**
 * Allow to use Generic Json Pamareters to read input {@link JSONObject}
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
public abstract class JsonParameter {

	protected Map<String, Object> values = new HashMap<>();

	protected void put(final String key, final Object value) {
		values.put(key, value);
	}

	protected String getString(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof String) {
			return (String) object;
		}
		return null;
	}

	protected Double getDouble(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof Double) {
			return (Double) object;
		}
		return null;
	}

	protected Integer getInteger(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof Integer) {
			return (Integer) object;
		}
		return null;
	}

	protected Delay getDelay(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof Delay) {
			return (Delay) object;
		}
		return null;
	}

	protected Location getLocation(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof Location) {
			return (Location) object;
		}
		return null;
	}

	protected OffsetDateTime getDateTime(final String key) {
		if (!values.containsKey(key)) {
			return null;
		}
		final Object object = values.get(key);
		if (object instanceof OffsetDateTime) {
			return (OffsetDateTime) object;
		}
		return null;
	}

	public abstract void parseJson(final JSONObject jsonObject);

	@Override
	public String toString() {
		return values.toString();
	}

	protected static Object extractJsonValue(final JSONObject jsonObject, final String key, final ParameterType type) {
		if (!jsonObject.has(key)) {
			return null;
		}
		try {
			switch (type) {
			case STRING:
				return jsonObject.getString(key);
			case NUMERICAL_DOUBLE:
				return jsonObject.getDouble(key);
			case NUMERICAL_INTEGER:
				return jsonObject.getInt(key);
			case IP_ADDRESS:
				return jsonObject.getString(key);
			case DATE_TIME:
				return TimeUtils.parseDateTimeFromString(jsonObject.getString(key));
			case DELAY:
				return Delay.fromString(jsonObject.getString(key));
			case LOCATION:
				final Country country = Country.fromString(jsonObject.getString(key));
				if (country != null) {
					return country;
				}
				return Continent.fromString(jsonObject.getString(key));
			default:
				return null;
			}
		} catch (final JSONException e) {
			return null;
		}
	}
}

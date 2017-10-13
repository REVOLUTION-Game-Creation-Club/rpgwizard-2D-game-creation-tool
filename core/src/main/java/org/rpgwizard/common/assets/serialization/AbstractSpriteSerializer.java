/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets.serialization;

import org.rpgwizard.common.assets.AbstractSprite;
import org.rpgwizard.common.assets.AssetException;
import org.json.JSONObject;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractSpriteSerializer extends AbstractJsonSerializer {

	protected <T extends AbstractSprite> T load(T sprite, JSONObject json)
			throws AssetException {
		sprite.setVersion(json.getDouble("version"));
		sprite.setName(json.getString("name"));
		sprite.setFrameRate(json.optDouble("frameRate"));
		sprite.setGraphics(deserializeStringMap(json.getJSONObject("graphics")));
		sprite.setAnimations(deserializeStringMap(json
				.getJSONObject("animations")));

		sprite.setBaseVector(
				deserializeSpriteVector(json.optJSONObject("baseVector")),
				false);
		sprite.setActivationVector(
				deserializeSpriteVector(json.optJSONObject("activationVector")),
				false);

		sprite.setBaseVectorOffset(
				deserializePoint(json.optJSONObject("baseVectorOffset")), false);
		sprite.setActivationVectorOffset(
				deserializePoint(json.optJSONObject("activationOffset")), false);

		return sprite;
	}

	protected <T extends AbstractSprite> T store(T sprite, JSONObject json)
			throws AssetException {
		json.put("version", FILE_FORMAT_VERSION);

		json.put("name", sprite.getName());
		json.put("frameRate", sprite.getFrameRate());
		json.put("graphics", serializeMap(sprite.getGraphics()));
		json.put("animations", serializeMap(sprite.getAnimations()));

		json.put("baseVector", serializeSpriteVector(sprite.getBaseVector()));
		json.put("activationVector",
				serializeSpriteVector(sprite.getActivationVector()));

		json.put("baseVectorOffset",
				serializePoint(sprite.getBaseVectorOffset()));
		json.put("activationOffset",
				serializePoint(sprite.getActivationVectorOffset()));

		return sprite;
	}

}

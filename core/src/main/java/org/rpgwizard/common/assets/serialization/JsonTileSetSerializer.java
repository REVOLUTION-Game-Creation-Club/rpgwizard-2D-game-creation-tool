/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets.serialization;

import org.rpgwizard.common.assets.AssetDescriptor;
import org.rpgwizard.common.assets.AssetException;
import org.rpgwizard.common.assets.AssetHandle;
import org.rpgwizard.common.assets.TileSet;
import org.rpgwizard.common.io.Paths;
import org.rpgwizard.common.utilities.CoreProperties;
import org.json.JSONObject;

/**
 *
 * @author Joshua Michael Daly
 */
public class JsonTileSetSerializer extends AbstractJsonSerializer {

    @Override
    public boolean serializable(AssetDescriptor descriptor) {
        final String ext = Paths.extension(descriptor.getURI());
        return (ext.equals(CoreProperties.getFullExtension("toolkit.tileset.extension.json")));
    }

    @Override
    public boolean deserializable(AssetDescriptor descriptor) {
        return serializable(descriptor);
    }

    @Override
    protected void load(AssetHandle handle, JSONObject json) throws AssetException {
        final TileSet tileSet = new TileSet(handle.getDescriptor(), json.optInt("tileWidth"),
                json.optInt("tileHeight"));

        tileSet.setVersion(json.getDouble("version"));
        tileSet.setName(json.optString("name"));
        tileSet.setImage(json.optString("image"));

        handle.setAsset(tileSet);
    }

    @Override
    protected void store(AssetHandle handle, JSONObject json) throws AssetException {
        super.store(handle, json);

        final TileSet tileSet = (TileSet) handle.getAsset();

        json.put("name", tileSet.getName());
        json.put("tileWidth", tileSet.getTileWidth());
        json.put("tileHeight", tileSet.getTileHeight());
        json.put("image", serializePath(tileSet.getImage()));
    }

}

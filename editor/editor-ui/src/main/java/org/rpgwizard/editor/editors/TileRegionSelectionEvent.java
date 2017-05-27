/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.editors;

import java.util.EventObject;
import org.rpgwizard.common.assets.Tile;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class TileRegionSelectionEvent extends EventObject {

	private final Tile[][] tiles;

	/**
	 *
	 * @param source
	 * @param tiles
	 */
	public TileRegionSelectionEvent(Object source, Tile[][] tiles) {
		super(source);
		this.tiles = tiles;
	}

	/**
	 *
	 * @return
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

}

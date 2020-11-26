/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets.board;

/**
 *
 * @author Joshua Michael Daly
 */
public class StartingPosition {

    public int x;
    public int y;
    public int layer;

    public StartingPosition() {
        x = 0;
        y = 0;
        layer = 0;
    }

    /**
     * Copy constructor.
     * 
     * @param startingPosition
     */
    public StartingPosition(StartingPosition startingPosition) {
        x = startingPosition.x;
        y = startingPosition.y;
        layer = startingPosition.layer;
    }

    public StartingPosition(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

}

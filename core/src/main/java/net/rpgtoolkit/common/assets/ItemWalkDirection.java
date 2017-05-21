/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public enum ItemWalkDirection {
    
    NORTH(0),
    SOUTH(1),
    EAST(2),
    WEST(3),
    NORTH_WEST(4),
    NORTH_EAST(5),
    SOUTH_WEST(6),
    SOUTH_EAST(7);
    
    private final int value;
    
    private ItemWalkDirection(int value) {
        this.value = value;
    }
    
    public int value() {
        return this.value;
    }
    
}

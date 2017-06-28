/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets;

/**
 *
 * @author Joshua Michael Daly
 */
public enum Equipment {

	HEAD("head"), CHEST("chest"), RIGHT_HAND("right-hand"), LEFT_HAND(
			"left-hand"), BOOTS("boots"), GLOVES("gloves"), ACCESSORY_1(
			"accessory-1"), ACCESSORY_2("accessory-2");

	private final String name;

	private Equipment(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	@Override
	public String toString() {
		return name;
	}

}
